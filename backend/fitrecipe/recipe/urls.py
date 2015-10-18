#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-13 20:40:03
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:48:21
from django.conf.urls import patterns, url

from .views import RecipeList, RecipeDetail, RecipeSearch, IngredientSearch, FoodSearch, SystemCheck

urlpatterns = patterns('',
    url(r'check/$', SystemCheck.as_view()),
    url(r'search/$', RecipeSearch.as_view()),
    url(r'search/ingredient/$', IngredientSearch.as_view()),
    url(r'search/food/$', FoodSearch.as_view()),
    url(r'list/$', RecipeList.as_view()),
    url(r'(\d+)/$', RecipeDetail.as_view()),
)
