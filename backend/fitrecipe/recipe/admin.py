#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-04-26 15:29:14
from django.contrib import admin

from .models import Recipe
# Register your models here.

admin.site.register(Recipe)
