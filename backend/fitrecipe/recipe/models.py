#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-04-26 15:30:14
from django.db import models
from django.conf import settings

from base.models import BaseModel


# Create your models here.
class Recipe(BaseModel):
    '''
    recipe models
    '''
    # 菜谱id号(auto)，菜谱封面url，菜谱名称，功效，烹饪时间，卡路里，收藏数(redis)
    img_height = models.PositiveIntegerField(null=True, blank=True)
    img_width = models.PositiveIntegerField(null=True, blank=True)
    img = models.ImageField(upload_to=settings.IMAGE_UPLOAD_ROOT, width_field='img_width', height_field='img_height')
    thumbnail = models.ImageField(upload_to=settings.IMAGE_UPLOAD_ROOT, null=True, blank=True)
    title = models.CharField(max_length=100)
    type = models.IntegerField(max_length=5, default=0)  # （0代表不限，1代表增肌，2代表减脂）
    duration = models.CharField(max_length=20, null=True, blank=True)
    calorie = models.CharField(max_length=100, null=True, blank=True)
