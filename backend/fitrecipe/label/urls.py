#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-20 18:13:19
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-20 18:15:44
from django.conf.urls import patterns, url

from .views import LabelList

urlpatterns = patterns('',
    url(r'list/$', LabelList.as_view()),
)
