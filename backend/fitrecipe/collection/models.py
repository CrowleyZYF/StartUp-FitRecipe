#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.db import models
from base.models import BaseModel
from accounts.models import Account
from recipe.models import Recipe
from article.models import Series
from theme.models import Theme

# Create your models here.
class Collection(BaseModel):
    owner = models.ForeignKey(Account)

    class Meta:
        abstract = True


class RecipeCollection(Collection):
    recipe = models.ForeignKey(Recipe)

    class Meta:
        verbose_name = u'菜谱收藏'
        verbose_name_plural = u'%s表' % verbose_name
        unique_together = (('recipe', 'owner'),)

    def __unicode__(self):
        return u'%s的菜谱收藏' % self.owner.nick_name


class SeriesCollection(Collection):
    series = models.ForeignKey(Series)

    class Meta:
        verbose_name = u'文章系列收藏'
        verbose_name_plural = u'%s表' % verbose_name
        unique_together = (('series', 'owner'),)

    def __unicode__(self):
        return u'%s的文章系列收藏' % self.owner.nick_name


class ThemeCollection(Collection):
    theme = models.ForeignKey(Theme)

    class Meta:
        verbose_name = u'主题收藏'
        verbose_name_plural = u'%s表' % verbose_name
        unique_together = (('theme', 'owner'),)

    def __unicode__(self):
        return u'%s的主题收藏' % self.owner.nick_name
