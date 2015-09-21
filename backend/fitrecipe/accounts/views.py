#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 14:50:49
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 17:53:08
import json

from rest_framework.authentication import TokenAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.authtoken.models import Token
from django.db import IntegrityError

from .models import Account, External, Evaluation
from .serializers import AccountSerializer, EvaluationSerializer
from base.views import BaseView, AuthView
from fitrecipe.utils import random_str


# Create your views here.
class LoginView(BaseView):
    # authentication_classes = (TokenAuthentication,)
    # permission_classes = (IsAuthenticated,)

    # def get(self, request, format=None):
    #     content = {
    #         'user': unicode(request.user),  # `django.contrib.auth.User` instance.
    #         'auth': unicode(request.auth),  # None
    #     }
    #     return Response(content)

    def post(self, request, format=None):
        '''
        正常登录
        '''
        data = json.loads(request.body)
        phone = data.get('phone')
        password = data.get('password')
        try:
            u = Account.objects.get(phone=phone)
            if password != u.password:
                return self.fail_response(401, 'password is not correct')
            token = Token.objects.get(user=u)
            result = AccountSerializer(u).data
            result['token'] = token.key
            return self.success_response(result)
        except Account.DoesNotExist:
            return self.fail_response(402, 'Account not existed')


class RegisterView(BaseView):
    def post(self, request, format=None):
        '''
        正常注册，需要手机号，密码
        '''
        data = json.loads(request.body)
        insert_data = dict()
        insert_data['nick_name'] = u'%s%s' % (random_str(scope='alphabet', num=5), random_str(scope='number', num=5))
        insert_data['username'] = 'normal_%s' % insert_data['nick_name']  # username 没有用的，但是 User 里不能是空的，所以随机一下
        insert_data['password'] = data.get('password')
        insert_data['phone'] = data.get('phone')
        try:
            u = Account.objects.create(**insert_data)
        except IntegrityError:
            return self.fail_response(400, 'Phone registed')
        token = Token.objects.create(user=u)
        result = AccountSerializer(u).data
        result['token'] = token.key
        return self.success_response(result)


class ThirdPartyLogin(BaseView):
    def post(self, request, format=None):
        '''
        第三方登录
        '''
        data = json.loads(request.body)
        external_source = data.get('external_source')
        external_id = data.get('external_id')
        nick_name = data.get('nick_name')
        # 直接获取用户，如果不存在，则创建
        try:
            e = External.objects.get(external_id=external_id, external_source=external_source)
            u = e.user
            token = Token.objects.get(user=u)
        except External.DoesNotExist:
            # 不存在这个第三方登录，先创建一个用户，再创建external
            username = 'external_%s' % random_str()
            u = Account.objects.create(
                username=username,
                password=random_str(num=32),
                phone=None,
                nick_name=nick_name,
                is_changed_nick=True)
            External.objects.create(
                user=u,
                external_source=external_source,
                external_id=external_id)
            token = Token.objects.create(user=u)
        result = AccountSerializer(u).data
        result['token'] = token.key
        return self.success_response(result)

class UploadEvaluationData(AuthView):
    def post(self, request, format=None):
        '''
        上传评测数据
        '''
        user = Account.find_account_by_user(request.user)
        evaluation = Evaluation()
        evaluation.user = user
        data = json.loads(request.body)
        evaluation.gender = int(data.get('gender'))
        evaluation.age = int(data.get('age'))
        evaluation.height = int(data.get('height'))
        evaluation.weight = float(data.get('weight'))
        evaluation.roughFat = int(data.get('roughFat'))
        evaluation.goalType = int(data.get('goalType'))
        evaluation.weightGoal = float(data.get('weightGoal'))
        evaluation.daysToGoal = int(data.get('daysToGoal'))
        evaluation.preciseFat = float(data.get('preciseFat'))
        evaluation.jobType = int(data.get('jobType'))
        evaluation.exerciseFrequency = int(data.get('exerciseFrequency'))
        evaluation.exerciseInterval = int(data.get('exerciseInterval'))
        try:
            e = Evaluation.objects.get(user=user)
            #update evaluation
            evaluation.pk = e.pk
            evaluation.save()
        except Evaluation.DoesNotExist:
            evaluation.save()
        except Evaluation.MultipleObjectsReturned:
            return self.fail_response(401, 'muiltiple evaluations exist')
        return self.success_response('added')

class DownloadEvaluationData(AuthView):
    def get(self, request, format=None):
        user = Account.find_account_by_user(request.user)
        try:
            e = Evaluation.objects.get(user=user)
            return self.success_response(EvaluationSerializer(e).data)
        except Evaluation.DoesNotExist:
            return self.fail_response(404, 'Evaluation data cannot be found')