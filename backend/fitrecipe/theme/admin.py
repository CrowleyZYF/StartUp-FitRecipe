#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:16:10
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 13:53:32
from django.contrib import admin
from .models import Theme
# Register your models here.


class ThemeAdmin(admin.ModelAdmin):
    list_display = ('id', 'title')
    list_display_links = ('title',)
    search_fields = ('title',)
    filter_horizontal = ('recipes',)


admin.site.register(Theme, ThemeAdmin)
