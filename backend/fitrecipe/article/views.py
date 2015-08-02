#!/usr/bin/env python
# -*- coding: utf-8 -*-
from base.views import BaseView
from .serializers import ArticleSerializer, ArticleTypeSerializer, SeriesSerializer
from .models import Article, ArticleType, Series

# Create your views here.
class ArticleDetail(BaseView):
    def get(self, request, id, format=None):
        article = Article.objects.get(pk=id)
        return self.success_response(ArticleSerializer(article).data)


class SeriesDetail(BaseView):
    def get(self, request, id, format=None):
        series = Series.objects.get(pk=id)
        return self.success_response(SeriesSerializer(series).data)

class ArticleTypeDetail(BaseView):
    def get(self, request, id, format=None):
        article_type = ArticleType.objects.get(pk=id)
        return self.success_response(ArticleTypeSerializer(article_type, context={'simple': False}).data)


class ArticleTypeList(BaseView):
    def get(self, request, format=None):
        article_types = ArticleType.objects.all()
        return self.success_response(ArticleTypeSerializer(article_types, many=True).data)
