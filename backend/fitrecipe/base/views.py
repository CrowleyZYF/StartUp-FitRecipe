#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-08 20:45:31
from django.http import Http404
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.authentication import TokenAuthentication
from rest_framework.permissions import IsAuthenticated

# Create your views here.
class BaseView(APIView):
    def get_object(self, model, pk):
        '''
        return instance of model whose pk is specified. 404 will be raised if instance not found.
        '''
        try:
            return model.objects.get(pk=pk)
        except model.DoesNotExist:
            raise Http404

    def success_response(self, body):
        r = {'status': 200, 'error_message': None, 'data': body}
        return Response(r)

    def fail_response(self, status_code, error_message):
        r = {'status': status_code, 'error_message': error_message, 'data': None}
        return Response(r, status=status_code)

class AuthView(BaseView):
    authentication_classes = (TokenAuthentication,)
    permission_classes = (IsAuthenticated,)
