#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-15 19:10:49
from rest_framework import serializers

from .models import Recipe, Component, Procedure, Label, Ingredient, Nutrition


class NutritionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutrition


class LabelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Label
        fields = ('id', 'name', 'type')


class IngredientSerializer(serializers.ModelSerializer):

    class Meta:
        model = Ingredient
        fields = ('id', 'name')


class ComponentSerializer(serializers.ModelSerializer):
    ingredient = IngredientSerializer()

    class Meta:
        model = Component
        fields = ('id', 'ingredient', 'amount', 'remark')


class ProcedureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Procedure
        fields = ('id', 'num', 'content', 'img')


class RecipeSerializer(serializers.ModelSerializer):
    meat_labels = LabelSerializer(many=True, read_only=True)  # 我不要看到那些created_time, updated_time
    time_labels = LabelSerializer(many=True, read_only=True)
    effect_labels = LabelSerializer(many=True, read_only=True)
    other_labels = LabelSerializer(many=True, read_only=True)
    component_set = ComponentSerializer(many=True, read_only=True)
    procedure_set = ProcedureSerializer(many=True, read_only=True)
    nutrition_set = serializers.DictField(source='get_nutrition')

    class Meta:
        model = Recipe
