#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:43:25
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 15:58:12
from django.conf.urls import patterns, url

from .views import ThemeDetail, ThemeList

urlpatterns = patterns('',
    url(r'list/$', ThemeList.as_view()),
    url(r'(\d+)/$', ThemeDetail.as_view()),
)
