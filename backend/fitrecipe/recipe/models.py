#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 21:24:01
from django.db import models

from base.models import BaseModel


class Recipe(BaseModel):
    '''
    recipe models
    '''
    # 菜谱id号(auto)，菜谱封面url，菜谱名称，功效，烹饪时间，卡路里，收藏数(redis)
    img = models.URLField(max_length=200)  # 图片全部使用 CDN
    thumbnail = models.URLField(max_length=200)
    title = models.CharField(max_length=100)
    duration = models.IntegerField()  # 烹饪时间
    effect_labels = models.ManyToManyField('Label', limit_choices_to={'type': '功效'}, related_name='effect_set', null=True, blank=True)
    time_labels = models.ManyToManyField('Label', limit_choices_to={'type': '用餐时间'}, related_name='time_set', null=True, blank=True)
    meat_labels = models.ManyToManyField('Label', limit_choices_to={'type': '食材'}, related_name='meat_set', null=True, blank=True)
    other_labels = models.ManyToManyField('Label', limit_choices_to={'type': '其他'}, related_name='other_set', null=True, blank=True)

    def __unicode__(self):
        return self.title


class Component(BaseModel):
    '''
    菜谱的食材构成
    '''
    recipe = models.ForeignKey('Recipe')
    ingredient = models.ForeignKey('Ingredient')
    amount = models.IntegerField()  # 克数 没有小数
    remark = models.CharField(max_length=100, null=True, blank=True)

    def __unicode__(self):
        return u'%s 的食材【%s】' % (self.recipe.title, self.ingredient.name)


class Procedure(BaseModel):
    '''
    操作步骤
    '''
    recipe = models.ForeignKey('Recipe')
    num = models.IntegerField()  # 第num步，应该和 recipe 一起作为 unique
    content = models.TextField()
    img = models.URLField(null=True, blank=True)

    class Meta:
        unique_together = (('recipe', 'num'),)  # 有必要嘛？是不是太强制了一点？

    def __unicode__(self):
        return u'%s的步骤 第%s步 ' % (self.recipe.title, self.num)


class Label(BaseModel):
    '''
    标签 (功效) 增肌 减脂 (用餐时间) 早餐 加餐 正餐 (食材) 鸡肉 鱼肉 牛肉 海鲜 蛋奶 果蔬 米面 点心 (其他标签) 酸甜 等等
    '''
    name = models.CharField(max_length=25)
    type = models.CharField(max_length=25)

    def __unicode__(self):
        return self.name


class Ingredient(BaseModel):
    name = models.CharField(max_length=25)
    # nutrition_set can get all nutritions

    def __unicode__(self):
        return self.name


class Nutrition(BaseModel):
    name = models.CharField(max_length=25)
    eng_name = models.CharField(max_length=100)
    amount = models.FloatField()
    unit = models.CharField(max_length=25)
    ingredient = models.ForeignKey('Ingredient')

    def __unicode__(self):
        return u'%s 的营养 【%s】' % (self.ingredient.name, self.name)
