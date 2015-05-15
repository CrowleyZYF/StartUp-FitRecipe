#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-15 20:27:21
from django.contrib import admin

from .models import Recipe, Component, Ingredient, Label, Nutrition, Procedure
# Register your models here.


class ComponentInline(admin.TabularInline):
    model = Component


class ProcedureInline(admin.TabularInline):
    model = Procedure


class NutritionInline(admin.TabularInline):
    model = Nutrition


class RecipeAdmin(admin.ModelAdmin):
    list_display = ('id', 'title',)
    list_display_links = ('title',)
    search_fields = ('title',)
    filter_horizontal = ('effect_labels', 'time_labels', 'meat_labels', 'other_labels')
    list_filter = ('effect_labels', 'time_labels', 'meat_labels', 'other_labels')
    inlines = (ComponentInline, ProcedureInline)


class IngredientAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'eng_name', 'ndbno')
    search_fields = ('name',)
    list_display_links = ('name',)
    inlines = (NutritionInline,)


class LabelAdmin(admin.ModelAdmin):
    # http://stackoverflow.com/questions/16235201/list-display-how-to-display-value-from-choices
    list_display = ('id', 'name', 'get_type_display')
    list_filter = ('type',)
    search_fields = ('name',)
    list_display_links = ('name',)


admin.site.register(Recipe, RecipeAdmin)
admin.site.register(Ingredient, IngredientAdmin)
admin.site.register(Label, LabelAdmin)
