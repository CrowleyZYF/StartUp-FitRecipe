#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 15:38:57
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-11 00:11:18
from .models import Account, External, OtherAuthor, Evaluation
from base.serializers import BaseSerializer


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

    class Meta:
        model = Evaluation
        fieds = ('id', 'author', 'gender', 'age', 'height', 'weight', 'roughFat', 'preciseFat', 'jobType', 'goalType',
                 'exerciseFrequency', 'exerciseInterval', 'weightGoal', 'daysToGoal')



