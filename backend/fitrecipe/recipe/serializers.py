#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-14 20:24:07
from rest_framework import serializers

from .models import Recipe, Component, Procedure, Label, Ingredient, Nutrition


class ProcedureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Procedure


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


class RecipeSerializer(serializers.ModelSerializer):
    meat_labels = LabelSerializer(many=True)  # 我不要看到那些created_time, updated_time
    time_labels = LabelSerializer(many=True)
    effect_labels = LabelSerializer(many=True)
    other_labels = LabelSerializer(many=True)
    component_set = ComponentSerializer(many=True)

    class Meta:
        model = Recipe
