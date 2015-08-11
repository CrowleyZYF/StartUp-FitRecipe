#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-10 16:17:18
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-06-10 22:33:20
from django.db import models
from base.models import BaseModel


# Create your models here.
class ArticleType(BaseModel):
    title = models.CharField(max_length=50, verbose_name=u'标题')
    introduce = models.TextField(verbose_name=u'描述')

    class Meta:
        verbose_name = u'类别'
        verbose_name_plural = verbose_name + u'表'

    def __unicode__(self):
        return self.title


class Series(BaseModel):
    title = models.CharField(max_length=50, verbose_name=u'标题')
    introduce = models.TextField(verbose_name=u'简介')
    img_cover = models.URLField(verbose_name=u'封面')
    recommend_img = models.URLField(null=True, blank=True, verbose_name=u'推荐封面')
    author = models.CharField(max_length=50, verbose_name=u'作者名字')
    author_avatar = models.URLField(max_length=200, verbose_name=u'作者头像')
    author_type = models.CharField(max_length=50, verbose_name=u'作者类型')
    article_type = models.ForeignKey(ArticleType, verbose_name=u'所属类别')
    collection_count = models.IntegerField(default=0, verbose_name=u'收藏数')

    class Meta:
        verbose_name = u'系列'
        verbose_name_plural = verbose_name + u'表'

    def __unicode__(self):
        return self.title

    def total_read_count(self):
        count = 0
        for a in self.article_set.all():
            count += a.read_count
        return count


class Article(BaseModel):
    title = models.CharField(max_length=50, verbose_name=u'标题')
    img_cover = models.URLField(max_length=200, verbose_name=u'封面图片')
    recommend_img = models.URLField(null=True, blank=True, verbose_name=u'推荐封面')
    content = models.TextField(verbose_name=u'正文')
    wx_url = models.URLField(blank=True, null=True, verbose_name=u'微信URL')
    series = models.ForeignKey(Series, verbose_name=u'所属系列')
    read_count = models.IntegerField(default=0, verbose_name=u'阅读数')

    class Meta:
        verbose_name = u'文章'
        verbose_name_plural = verbose_name + u'表'

    def __unicode__(self):
        return self.title

    def incr_read_count(self):
        self.read_count += 1
        self.save()
        return self
