#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-13 20:43:53
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:47:30

from django.conf.urls import patterns, url

from .views import LoginView, RegisterView, ThirdPartyLogin

urlpatterns = patterns('',
    url(r'login/$', LoginView.as_view()),
    url(r'register/$', RegisterView.as_view()),
    url(r'thirdparty/$', ThirdPartyLogin.as_view()),
)
