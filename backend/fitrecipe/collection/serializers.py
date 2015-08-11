#!/usr/bin/env python
# -*- coding: utf-8 -*-

from base.serializers import BaseSerializer
from recipe.serializers import RecipeSerializer
from theme.serializers import ThemeSerializer
from article.serializers import SeriesSerializer
from .models import RecipeCollection, SeriesCollection, ThemeCollection

class RecipeCollectionSerializer(BaseSerializer):
    recipe = RecipeSerializer(value=('id','author', 'img', 'thumbnail', 'recommend_img', 'recommend_thumbnail', 'title', 'introduce', 'duration', 'effect_labels', 'meat_labels', 'other_labels', 'calories', 'collection_count'))

    class Meta:
        model = RecipeCollection


class ThemeCollectionSerializer(BaseSerializer):
    theme = ThemeSerializer()

    class Meta:
        model = ThemeCollection


class SeriesCollectionSerializer(BaseSerializer):
    series = SeriesSerializer(value=('id','total_read_count', 'title', 'introduce', 'recommend_img', 'author', 'author_avatar', 'article_type', 'author_type'))

    class Meta:
        model = SeriesCollection
