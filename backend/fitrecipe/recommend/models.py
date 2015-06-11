#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-10 22:41:16
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-06-11 16:36:28
from django.db import models
from base.models import BaseModel
from recipe.models import Recipe
from django.core.validators import MaxValueValidator, MinValueValidator
# Create your models here.


class Recommend(BaseModel):
    name = models.CharField(max_length=25, verbose_name=u'名字')
    recipes = models.ManyToManyField(Recipe, verbose_name=u'推荐菜谱')
    start_time = models.IntegerField(validators=[MaxValueValidator(23), MinValueValidator(0)], verbose_name=u'开始时间', help_text=u'开始推荐时间，0~23')
    end_time = models.IntegerField(validators=[MaxValueValidator(23), MinValueValidator(0)], verbose_name=u'结束时间', help_text=u'结束推荐时间，0~23')

    class Meta:
        verbose_name = u'推荐'
        verbose_name_plural = verbose_name + u'表'

    def __unicode__(self):
        return self.name

    @classmethod
    def get_recommend_recipes(cls, hour):
        r = cls.objects.filter(start_time__lte=hour, end_time__gte=hour)
        if r.count() < 1:
            r = cls.objects.all()
        try:
            recipes = r[0].recipes.all()
            return recipes
        except IndexError:
            return []

