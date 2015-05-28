#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:43:25
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 17:45:38
from django.conf.urls import patterns, url

from .views import ThemeDetail, ThemeList, ThemeRecipeList

urlpatterns = patterns('',
    url(r'list/$', ThemeList.as_view()),
    url(r'(\d+)/recipes/$', ThemeRecipeList.as_view()),
    url(r'(\d+)/$', ThemeDetail.as_view()),
)
