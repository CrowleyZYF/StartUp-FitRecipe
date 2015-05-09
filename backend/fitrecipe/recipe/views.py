#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-08 20:42:24

from base.views import BaseView
from .models import Recipe
from .serializers import RecipeSerializer
# Create your views here.


class RecipeList(BaseView):
    def get(self, request, format=None):
        '''
        List all recipes
        '''
        recipes = Recipe.objects.all()
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
