#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:16:10
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 15:08:25
from django.db import models

from base.models import BaseModel
from recipe.models import Recipe
# Create your models here.


class Theme(BaseModel):
    '''
    Theme Models
    '''
    title = models.CharField(max_length=25, verbose_name=u'主题名字')
    content = models.TextField(null=True, blank=True, verbose_name=u'主题描述')
    img = models.URLField(max_length=200, verbose_name=u'主题图片')
    thumbnail = models.URLField(max_length=200, verbose_name=u'缩略图图片')
    recipes = models.ManyToManyField(Recipe, verbose_name=u'包含的菜谱')

    class Meta:
        verbose_name = u'主题'
        verbose_name_plural = u'%s表' % verbose_name

    def __unicode__(self):
        return self.title
