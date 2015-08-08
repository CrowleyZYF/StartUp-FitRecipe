#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.db import models
from base.models import BaseModel
from accounts.models import Account
from recipe.models import Recipe

# Create your models here.
class Comment(BaseModel):
    '''
    Comment
    '''
    author = models.ForeignKey(Account, verbose_name=u'作者')
    reply = models.ForeignKey(Account, null=True, blank=True, related_name=u'reply_set', verbose_name=u'回复')
    recipe = models.ForeignKey(Recipe, verbose_name=u'菜谱')
    content = models.CharField(max_length=200, verbose_name=u'评论')

    class Meta:
        verbose_name = u'评论'
        verbose_name_plural = u'%s表' % verbose_name

    def __unicode__(self):
        return u'%s在%s下的评论' % (self.author.phone or self.author.nick_name, self.recipe.title)
