#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:53:04
from django.contrib import admin

from .models import Recipe, Component, Ingredient, Label, Nutrition, Procedure
# Register your models here.

admin.site.register(Recipe)
admin.site.register(Component)
admin.site.register(Ingredient)
admin.site.register(Label)
admin.site.register(Nutrition)
admin.site.register(Procedure)
