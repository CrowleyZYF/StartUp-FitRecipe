#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-14 20:27:49
from rest_framework import serializers
from .models import Recipe, Component, Procedure, Ingredient, Nutrition
from accounts.serializers import OtherAuthorSerializer
from base.serializers import BaseSerializer
from label.serializers import LabelSerializer
from comment.serializers import CommentSerializer

class NutritionSerializer(BaseSerializer):
    class Meta:
        model = Nutrition


class IngredientSerializer(BaseSerializer):
    nutrition_set = serializers.DictField(source='get_nutrition')

    class Meta:
        model = Ingredient
        fields = ('id', 'name', 'nutrition_set')


class ComponentSerializer(BaseSerializer):
    ingredient = IngredientSerializer(value=('id', 'name'))

    class Meta:
        model = Component
        fields = ('id', 'ingredient', 'amount', 'remark')


class ProcedureSerializer(BaseSerializer):
    class Meta:
        model = Procedure
        fields = ('id', 'num', 'content', 'img')


class RecipeSerializer(BaseSerializer):
    meat_labels = LabelSerializer(many=True, read_only=True)  # 我不要看到那些created_time, updated_time
    time_labels = LabelSerializer(many=True, read_only=True)
    effect_labels = LabelSerializer(many=True, read_only=True)
    other_labels = LabelSerializer(many=True, read_only=True)
    component_set = ComponentSerializer(value=('amount', 'ingredient', 'remark'), many=True, read_only=True)
    procedure_set = ProcedureSerializer(value=('content', 'num', 'img'), many=True, read_only=True)
    nutrition_set = serializers.DictField(source='get_nutrition')
    macro_element_ratio = serializers.CharField()
    total_amount = serializers.CharField(source='get_total_amount')
    protein_ratio = serializers.CharField()
    fat_ratio = serializers.CharField()
    author = OtherAuthorSerializer(value=('id', 'nick_name', 'avatar'), read_only=True)
    comment_set = CommentSerializer(source='get_latest_comment', many=True)

    class Meta:
        model = Recipe

    def to_representation(self, obj):
        '''
        加上简单模式，用于列表展示
        '''
        r = super(RecipeSerializer, self).to_representation(obj)
        simple = self.context.get('simple', True)
        if simple:
            # 去掉 component_set, procedure_set, nutrition_set
            pop_keys = ('component_set', 'procedure_set', 'nutrition_set', 'comment_set')
            for k in pop_keys:
                r.pop(k, None)
        return r
