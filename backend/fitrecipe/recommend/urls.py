#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-11 16:29:36
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-06-11 16:31:13
from django.conf.urls import patterns, url
from .views import RecommendRecipes

urlpatterns = patterns('',
    url(r'recipe/$', RecommendRecipes.as_view()),
)