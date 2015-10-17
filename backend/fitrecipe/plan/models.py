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


class Plan(BaseModel):
    '''
    计划
    '''
    title = models.CharField(max_length=100, default='personal plan')
    img = models.URLField(max_length=200, null=True, blank=True)
    cover = models.URLField(max_length=200, null=True, blank=True)
    brief = models.TextField(default='')
    internal_label = models.TextField(default=0, help_text=u'0-未指定，1-食谱，2-食材')
    inrtoduce = models.TextField(default='')
    difficulty = models.IntegerField(default=1)
    delicious = models.IntegerField(default=3)
    benifit = models.IntegerField(default=0)  # 适宜人群:0-无所谓 1-减脂、2-增肌
    total_days = models.IntegerField(default=1)
    dish_headcount = models.IntegerField(default=1)  # 有多少人使用了这个计划
    author = models.ForeignKey(PlanAuthor, null=True, blank=True)
    user = models.ForeignKey(Account, null=True, blank=True)
    is_personal = models.BooleanField(default=True)
    authored_date = models.DateField(auto_now_add=True)

    def __unicode__(self):
        return self.title

    def delete_routines(self):
        self.routine_set.all().delete()


class Routine(BaseModel):
    '''
    一个计划的一天
    '''
    day = models.IntegerField(default=1, help_text=u'Plan 中的第几天')
    plan = models.ForeignKey(Plan)

    def __unicode__(self):
        try:
            return u'%s - %s' % (self.plan.title, self.day)
        except Plan.DoesNotExist:
            return u'nothing'

    def delete(self):
        self.dish_set.all().delete()
        super(Routine, self).delete()


class Dish(BaseModel):
    '''
    每一顿吃的，早餐午餐啥的
    '''
    type = models.IntegerField(help_text=u'0-早餐 1-上午加餐 2-午餐 3-下午加餐 5-晚餐')
    routine = models.ForeignKey(Routine)

    def get_chinese_type(self):
        trans = [u'早餐', u'上午加餐', u'午餐', u'下午加餐', u'晚餐']
        return trans[self.type] or u'未定义'

    def delete(self):
        self.singleingredient_set.all().delete()
        self.singlerecipe_set.all().delete()
        super(Dish, self).delete()

    def __unicode__(self):
        return u'%s 的 %s' % (str(self.routine), self.get_chinese_type())


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


class Calendar(BaseModel):
    '''
    参加 plan
    '''
    user = models.ForeignKey(Account)
    plan = models.ForeignKey(Plan)
    joined_date = models.DateField()

    class Meta:
        unique_together = (('user', 'joined_date'),)


class Punch(BaseModel):
    '''
    打卡记录
    '''
    user = models.ForeignKey(Account)
    type = models.IntegerField()
    img = models.URLField(max_length=200)
    date = models.DateField(auto_now_add=True)
    state = models.IntegerField(default=10) # 10 - 正常， －10 删除
