#!/usr/bin/env python
# -*- coding: utf-8 -*-

from rest_framework import serializers
from base.serializers import BaseSerializer
from .models import Article, Series, ArticleType

class ArticleSerializer(BaseSerializer):

    class Meta:
        model = Article


class SeriesSerializer(BaseSerializer):
    article_set = ArticleSerializer(value=('id', 'title', 'img_cover', 'created_time', 'read_count'), many=True)
    total_read_count = serializers.IntegerField()

    class Meta:
        model = Series

    def to_representation(self, obj):
        '''
        加上简单模式，用于列表展示
        '''
        r = super(SeriesSerializer, self).to_representation(obj)
        simple = self.context.get('simple', True)
        if simple:
            # 去掉 component_set, procedure_set, nutrition_set
            try:
                r.pop('article_set')
            except KeyError:
                pass
        return r


class ArticleTypeSerializer(BaseSerializer):
    series_set = SeriesSerializer(value=('id', 'title', 'author', 'author_avatar', 'author_type'), many=True)

    class Meta:
        model = ArticleType

    def to_representation(self, obj):
        '''
        加上简单模式，用于列表展示
        '''
        r = super(ArticleTypeSerializer, self).to_representation(obj)
        simple = self.context.get('simple', True)
        if simple:
            # 去掉 component_set, procedure_set, nutrition_set
            r.pop('series_set')
        return r
