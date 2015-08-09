#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import patterns, url

from .views import CommentCreate,CommentList

urlpatterns = patterns('',
    url(r'create/$', CommentCreate.as_view()),
    url(r'(\d+)/list/$', CommentList.as_view()),
)
