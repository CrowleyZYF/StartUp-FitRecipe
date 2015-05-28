#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-20 18:12:12
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-20 18:13:08

from .models import Label
from base.serializers import BaseSerializer


class LabelSerializer(BaseSerializer):
    class Meta:
        model = Label
        fields = ('id', 'name', 'type')
