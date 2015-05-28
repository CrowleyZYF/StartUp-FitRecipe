#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:30:17
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 17:43:52

from .models import Theme
from base.serializers import BaseSerializer


class ThemeSerializer(BaseSerializer):
    '''
    这个 Serializer 不包含 recipes，菜谱列表分开取，因为要作分页
    '''
    class Meta:
        model = Theme
        fields = ('id', 'created_time', 'updated_time', 'title', 'content', 'img', 'thumbnail')

    def to_representation(self, obj):
        '''
        加上简单模式，用于列表展示
        '''
        r = super(ThemeSerializer, self).to_representation(obj)
        r['recipe_count'] = obj.recipes.count()
        return r
