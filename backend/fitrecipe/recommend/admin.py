#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-10 22:41:16
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-06-11 14:16:22
from django.contrib import admin
from .models import Recommend
# Register your models here.


class RecommendAmdmin(admin.ModelAdmin):
    list_display = ('id', 'name')
    list_display_links = ('name',)
    filter_horizontal = ('recipes',)


admin.site.register(Recommend, RecommendAmdmin)