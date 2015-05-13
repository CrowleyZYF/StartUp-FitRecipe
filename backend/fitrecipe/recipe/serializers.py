#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 21:40:38
from rest_framework import serializers

from .models import Recipe, Component, Procedure, Label, Ingredient, Nutrition


class ComponentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Component


class ProcedureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Procedure


class LabelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Label
        fields = ('id', 'name', 'type')


class IngredientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ingredient


class NutritionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutrition


class RecipeSerializer(serializers.ModelSerializer):
    meat_labels = LabelSerializer(many=True)  # 我不要看到那些created_time, updated_time
    time_labels = LabelSerializer(many=True)
    effect_labels = LabelSerializer(many=True)
    other_labels = LabelSerializer(many=True)

    class Meta:
        model = Recipe
        depth = 1
