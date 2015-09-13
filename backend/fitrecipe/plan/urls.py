#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import patterns, url

from .views import PlanList, PlanDetail

urlpatterns = patterns('',
    url(r'list/$', PlanList.as_view()),
    url(r'(\d+)/$', PlanDetail.as_view()),
)
