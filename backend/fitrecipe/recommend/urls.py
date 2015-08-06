#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-11 16:29:36
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 20:30:16
from django.conf.urls import patterns, url
from .views import HomepageRecommends

urlpatterns = patterns('',
    url(r'$', HomepageRecommends.as_view()),
)
