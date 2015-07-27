#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-06-10 22:41:16
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 17:02:03
from django.contrib import admin
from ajax_select import make_ajax_form
from ajax_select.admin import AjaxSelectAdmin
from .models import Recommend, RecommendTheme
# Register your models here.


class RecommendAmdmin(admin.ModelAdmin):
    list_display = ('id', 'name')
    list_display_links = ('name',)
    filter_horizontal = ('recipes',)


class RecommendThemeAmdmin(AjaxSelectAdmin):
    list_display = ('id', 'theme')
    list_display_links = ('theme',)
    form = make_ajax_form(RecommendTheme, {'theme': 'theme'})


admin.site.register(Recommend, RecommendAmdmin)
admin.site.register(RecommendTheme, RecommendThemeAmdmin)