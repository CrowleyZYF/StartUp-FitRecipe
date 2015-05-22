#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-20 18:15:26

from base.views import BaseView
from .models import Recipe
from .serializers import RecipeSerializer
# Create your views here.


class RecipeList(BaseView):

    def get(self, request, format=None):
        '''
        List all recipes

        category=1,2&funtion=3,4&time=5,6&start=10&num=10&order
        '''
        meat_labels = request.GET.get('meat', None)  # meat_labels
        effect_labels = request.GET.get('effect', None)  # effect_labels
        time_labels = request.GET.get('time', None)
        start = request.GET.get('start', '0')
        num = request.GET.get('num', '10')
        order = request.GET.get('order', 'calories')
        desc = request.GET.get('desc', 'false')
        # https://docs.djangoproject.com/en/1.8/ref/models/querysets/#when-querysets-are-evaluated
        recipes = Recipe.get_recipe_list(meat_labels, effect_labels, time_labels, order, desc, start, num)
        serializer = RecipeSerializer(recipes, many=True)
        return self.success_response(serializer.data)


class RecipeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific recipe.
        '''
        recipe = self.get_object(Recipe, pk)
        serializer = RecipeSerializer(recipe, context={'simple': False})
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
