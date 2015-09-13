#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.db import models

from base.models import BaseModel
from recipe.models import Recipe, Ingredient
from accounts.models import Account

class PlanAuthor(BaseModel):
    '''
    plan author
    '''
    name = models.CharField(max_length=100)
    type = models.IntegerField() # 计划作者类型：计划作者类型
    avatar = models.URLField()
    job_title = models.CharField(max_length=100, null=True, blank=True)  # 职称
    fit_duration = models.CharField(max_length=100, null=True, blank=True)  # 健身年限
    fat = models.CharField(max_length=100, null=True, blank=True) # 体脂
    introduce = models.TextField(null=True, blank=True)

    def __unicode__(self):
        return self.name


class Routine(BaseModel):
    '''
    一个计划的一天
    '''
    day = models.IntegerField(help_text=u'Plan 中的第几天')
    title = models.CharField(max_length=200, help_text=u'起一个你自己看得懂的名字，搜索筛选用')

    def __unicode__(self):
        return u'%s - 第 %d 天' % (self.title, self.day)


class Plan(BaseModel):
    '''
    计划
    '''
    title = models.CharField(max_length=100)
    img = models.URLField(max_length=200)
    inrtoduce = models.TextField()
    difficulty = models.IntegerField(default=1)
    delicious = models.IntegerField(default=3)
    benifit = models.IntegerField()  # 适宜人群:增肌、减脂
    total_days = models.IntegerField()
    dish_headcount = models.IntegerField()  # 选用人数
    author = models.ForeignKey(PlanAuthor)
    routine = models.ManyToManyField(Routine)

    def __unicode__(self):
        return self.title


class PersonalPlan(BaseModel):
    user = models.ForeignKey(Account)
    routine = models.ManyToManyField(Routine)


class Dish(BaseModel):
    '''
    每一顿吃的，早餐午餐啥的
    '''
    type = models.IntegerField(help_text=u'0-早餐 1-上午加餐 2-午餐 3-下午加餐 5-晚餐')
    routine = models.ForeignKey(Routine)

    def get_chinese_type(self):
        trans = [u'早餐', u'上午加餐', u'午餐', u'下午加餐', u'晚餐']
        return trans[self.type] or '未定义'

    def __unicode__(self):
        return u'%s 的 %s' % (self.routine.title, self.get_chinese_type())


class SingleIngredient(BaseModel):
    '''
    单个食材
    '''
    ingredient = models.ForeignKey(Ingredient)
    amount = models.IntegerField()  # 单位都是g
    dish = models.ForeignKey(Dish)

    def __unicode__(self):
        return u'%dg %s' % (self.amount, self.ingredient.name)


class SingleRecipe(BaseModel):
    '''
    单个菜谱
    '''
    recipe = models.ForeignKey(Recipe)
    amount = models.IntegerField()
    dish = models.ForeignKey(Dish)

    def __unicode__(self):
        return '%dg %s' % (self.amount, self.recipe.title)


