#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:52:14
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:32:12
from rest_framework import serializers

from .models import Recipe, Component, Procedure, Label, Ingredient, Nutrition


class RecipeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Recipe


class ComponentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Component


class ProcedureSerializer(serializers.ModelSerializer):
    class Meta:
        model = Procedure


class LabelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Label


class IngredientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ingredient


class NutritionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutrition
