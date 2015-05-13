#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-13 20:40:03
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:48:21
from django.conf.urls import patterns, url

from .views import RecipeList, RecipeDetail

urlpatterns = patterns('',
    url(r'list/$', RecipeList.as_view()),
    url(r'(\d+)/$', RecipeDetail.as_view()),
)
