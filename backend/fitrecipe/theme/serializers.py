#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:30:17
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 16:03:22

from .models import Theme
from recipe.serializers import RecipeSerializer
from base.serializers import BaseSerializer


class ThemeSerializer(BaseSerializer):
    recipes = RecipeSerializer(value=('id', 'meat_labels', 'time_labels', 'effect_labels', 'other_labels', 'author', 'img', 'thumbnail', 'title', 'duration', 'calories'), many=True)

    class Meta:
        model = Theme

    def to_representation(self, obj):
        '''
        加上简单模式，用于列表展示
        '''
        r = super(ThemeSerializer, self).to_representation(obj)
        simple = self.context.get('simple', True)
        if simple:
            recipes = r.pop('recipes', [])
            r['recipe_count'] = len(recipes)
        return r
