#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 15:38:57
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-11 00:11:18
from .models import Account, External, OtherAuthor, Evaluation
from base.serializers import BaseSerializer
from django.core.validators import MinValueValidator, MaxValueValidator

from rest_framework import serializers

class ExternalSerializer(BaseSerializer):
    class Meta:
        model = External
        fields = ('id', 'external_source', 'external_id')


class AccountSerializer(BaseSerializer):
    externals = ExternalSerializer(many=True)

    class Meta:
        model = Account
        fields = ('id', 'nick_name', 'externals', 'avatar', 'is_changed_nick', 'phone', 'is_official')


class OtherAuthorSerializer(BaseSerializer):
    class Meta:
        model = OtherAuthor
        fields = ('id', 'nick_name', 'sex', 'avatar')

class EvaluationSerializer(BaseSerializer):
    user = AccountSerializer(value=('id', 'avatar', 'nick_name', 'is_official'))
    gender = serializers.IntegerField(min_value=0, max_value=1)
    age = serializers.IntegerField(min_value=5)
    #身高单位为cm
    height = serializers.IntegerField(min_value=0)
    weight = serializers.FloatField(min_value=0.0)
    #int, 0~4
    roughFat = serializers.IntegerField(min_value=0, max_value=4)
    preciseFat = serializers.FloatField(min_value=0.0)
    #int, 0~3
    jobType = serializers.IntegerField(min_value=0, max_value=3)
    #int, 0增肌， 1减脂
    goalType = serializers.IntegerField(min_value=0, max_value=1)
    exerciseFrequency = serializers.IntegerField()
    exerciseInterval = serializers.IntegerField(min_value=0, max_value=3)
    weightGoal = serializers.FloatField(min_value=0.0)
    daysToGoal = serializers.IntegerField(min_value=0)
    class Meta:
        model = Evaluation
        fields = ('id', 'user', 'gender', 'age', 'height', 'weight', 'roughFat', 'preciseFat', 'jobType', 'goalType',
                 'exerciseFrequency', 'exerciseInterval', 'weightGoal', 'daysToGoal')



