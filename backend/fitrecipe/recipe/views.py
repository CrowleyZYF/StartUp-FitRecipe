#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-20 12:03:42

from base.views import BaseView
from .models import Recipe, Component, Ingredient, Label, Nutrition, Procedure
from .serializers import RecipeSerializer, ComponentSerializer, LabelSerializer, IngredientSerializer, NutritionSerializer, ProcedureSerializer
# Create your views here.


class RecipeList(BaseView):
    def _split_labels_into_list(self, labels):
        tmp = list()
        for v in labels.split(','):
            try:
                tmp.append(int(v))  # 转数字
            except (TypeError, ValueError):
                continue
        return tmp

    def get(self, request, format=None):
        '''
        List all recipes

        category=1,2&funtion=3,4&time=5,6&start=10&num=10&order
        '''
        meat_labels = request.GET.get('meat', None)  # meat_labels
        effect_labels = request.GET.get('effect', None)  # effect_labels
        time_labels = request.GET.get('time', None)
        start = request.GET.get('start', 0)
        num = request.GET.get('num', 10)
        order = request.GET.get('order', 'cal')
        # https://docs.djangoproject.com/en/1.8/ref/models/querysets/#when-querysets-are-evaluated
        recipes = Recipe.objects.all()
        if meat_labels is not None:
            # 对 食材 进行筛选
            recipes = recipes.filter(meat_labels__id__in=self._split_labels_into_list(meat_labels))
        if effect_labels is not None:
            recipes = recipes.filter(effect_labels__id__in=self._split_labels_into_list(effect_labels))
        if time_labels is not None:
            recipes = recipes.filter(time_labels__id__in=self._split_labels_into_list(time_labels))
        serializer = RecipeSerializer(recipes, many=True)
        return self.success_response(serializer.data)


class RecipeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific recipe.
        '''
        recipe = self.get_object(Recipe, pk)
        serializer = RecipeSerializer(recipe)
        return self.success_response(serializer.data)


class RecipeRecommand(BaseView):
    def get(self, request, pk, format=None):
        '''
        return three recommand recipes list

        1. 首页推荐内容
        2. 首页主题封面
        3. 首页更新内容
        '''
        result = {
            'recommand': list(),
            'theme': list(),
            'update': list(),
        }
        return self.success_response(result)
