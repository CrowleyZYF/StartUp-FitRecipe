#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:16:10
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 17:44:24
from base.views import BaseView
from .serializers import ThemeSerializer
from .models import Theme
from recipe.serializers import RecipeSerializer
from accounts.models import Account
from collection.models import ThemeCollection


# Create your views here.
class ThemeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        reutrn a specific Theme
        '''
        theme = self.get_object(Theme, pk)
        result = ThemeSerializer(theme).data
        try:
            user = Account.find_account_by_user(request.user)
            has_collected = ThemeCollection.has_collected(theme, user)
        except Account.DoesNotExist:
            has_collected = False
        result['has_collected'] = has_collected
        return self.success_response(result)


class ThemeList(BaseView):
    def get(self, request, format=None):
        '''
        Theme List
        '''
        themes = Theme.objects.all()
        serializer = ThemeSerializer(themes, many=True)
        return self.success_response(serializer.data)


class ThemeRecipeList(BaseView):
    def get(self, request, theme_pk, format=None):
        '''
        return recipes of theme whose id is theme_pk
        '''
        num = self.request.GET.get('num', 5)
        start = self.request.GET.get('start', 0)
        t = Theme.objects.get(pk=theme_pk)
        recipes = t.get_recipes(start, num)
        serializer = RecipeSerializer(recipes, context={'simple': True}, many=True)
        return self.success_response(serializer.data)
