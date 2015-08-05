#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import patterns, url

from .views import ArticleDetail, ArticleTypeDetail, ArticleTypeList, SeriesDetail, SeriesList

urlpatterns = patterns('',
    url(r'series/(\d+)/$', SeriesDetail.as_view()),
    url(r'series/list/$', SeriesList.as_view()),
    url(r'type/list/$', ArticleTypeList.as_view()),
    url(r'type/(\d+)/$', ArticleTypeDetail.as_view()),
    url(r'(\d+)/$', ArticleDetail.as_view()),
)
