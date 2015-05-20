#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-19 00:26:42
from rest_framework import serializers
from .models import Recipe, Component, Procedure, Label, Ingredient, Nutrition
from accounts.serializers import AccountSerializer
from base.serializers import BaseSerializer


class NutritionSerializer(BaseSerializer):
    class Meta:
        model = Nutrition


class LabelSerializer(BaseSerializer):
    class Meta:
        model = Label
        fields = ('id', 'name', 'type')


class IngredientSerializer(BaseSerializer):

    class Meta:
        model = Ingredient
        fields = ('id', 'name')


class ComponentSerializer(BaseSerializer):
    ingredient = IngredientSerializer()

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
    author = AccountSerializer(value=('id', 'nick_name', 'avatar'), read_only=True)

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
            pop_keys = ('component_set', 'procedure_set', 'nutrition_set')
            for k in pop_keys:
                r.pop(k)
        return r
