#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import patterns, url

from .views import PlanList, PlanDetail, CalendarList, LastPlan, PunchList, DeletePunch

urlpatterns = patterns('',
    url(r'list/$', PlanList.as_view()),
    url(r'punch/$', PunchList.as_view()),
    url(r'punch/(\d+)/delete$', DeletePunch.as_view()),
    url(r'current/$', LastPlan.as_view()),
    url(r'calendar/$', CalendarList.as_view()),
    url(r'(\d+)/$', PlanDetail.as_view()),
)
