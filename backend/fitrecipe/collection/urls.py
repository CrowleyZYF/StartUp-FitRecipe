#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import patterns, url

from .views import CollectionCreate, CollectionDetail

urlpatterns = patterns('',
    url(r'create/$', CollectionCreate.as_view()),
    url(r'list/(\w+)/$', CollectionDetail.as_view()),
)
