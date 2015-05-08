#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 15:38:57
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-04 15:40:13
from rest_framework import serializers

from .models import Account


class AccountSerializer(serializers.ModelSerializer):
    class Meta:
        model = Account
