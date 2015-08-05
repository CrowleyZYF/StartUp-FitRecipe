#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 16:47:11

from base.views import BaseView
from .models import Recipe
from .serializers import RecipeSerializer
from label.models import Label
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


class RecipeSearch(BaseView):
    def get(self, request, format=None):
        '''
        search
        '''
        keyword = request.GET.get('keyword', None)
        start = abs(int(request.GET.get('start', 0)))
        num = abs(int(request.GET.get('num', 10)))
        if keyword is None:
            return self.success_response([])
        r = Recipe.objects.filter(title__contains=keyword)
        labels = Label.objects.filter(name__contains=keyword)
        tag_list = []
        for l in labels:
            if l.type == u'功效':
                tag_list = tag_list + list(l.effect_set.all())
            elif l.type == u'用餐时间':
                tag_list = tag_list + list(l.time_set.all())
            elif l.type == u'食材':
                tag_list = tag_list + list(l.meat_set.all())
            elif l.type == u'其他':
                tag_list = tag_list + list(l.other_set.all())
        tag_list = list(set(tag_list).difference(set(r)))
        final = list(r) + tag_list
        return self.success_response(RecipeSerializer(final[start: start + num], many=True).data)
