#!/usr/bin/env python
# -*- coding: utf-8 -*-
from base.views import BaseView
from .serializers import ArticleSerializer, ArticleTypeSerializer, SeriesSerializer
from .models import Article, ArticleType, Series

# Create your views here.
class ArticleDetail(BaseView):
    def get(self, request, id, format=None):
        article = Article.objects.get(pk=id)
        article.incr_read_count()
        return self.success_response(ArticleSerializer(article).data)


class SeriesDetail(BaseView):
    def get(self, request, id, format=None):
        series = Series.objects.get(pk=id)
        return self.success_response(SeriesSerializer(series, context={'simple': False}).data)

class ArticleTypeDetail(BaseView):
    def get(self, request, id, format=None):
        article_type = ArticleType.objects.get(pk=id)
        return self.success_response(ArticleTypeSerializer(article_type, context={'simple': False}).data)


class ArticleTypeList(BaseView):
    def get(self, request, format=None):
        article_types = ArticleType.objects.all()
        return self.success_response(ArticleTypeSerializer(article_types, many=True).data)


class SeriesList(BaseView):
    def get(self, request, format=None):
        type = request.GET.get('type', None)
        if type is None:
            series = Series.objects.all()
        else:
            result = []
            type_list = type.split(',')
            series = Series.objects.filter(article_type__id__in = type_list)
        return self.success_response(SeriesSerializer(series, many=True).data)
