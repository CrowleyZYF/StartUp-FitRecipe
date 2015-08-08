#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-14 20:27:43
from django.contrib import admin
from django.utils.html import format_html_join
from django.utils.safestring import mark_safe
from ajax_select import make_ajax_form
from ajax_select.admin import AjaxSelectAdminTabularInline

from .models import Recipe, Component, Ingredient, Nutrition, Procedure
# Register your models here.


class ComponentInline(AjaxSelectAdminTabularInline):
    model = Component

    form = make_ajax_form(Component, {'ingredient': 'ingredient'})


class ProcedureInline(admin.TabularInline):
    model = Procedure


class NutritionInline(admin.TabularInline):
    model = Nutrition


class RecipeAdmin(admin.ModelAdmin):
    list_display = ('id', 'title', 'calories', 'duration')
    list_display_links = ('title',)
    search_fields = ('title',)
    filter_horizontal = ('effect_labels', 'time_labels', 'meat_labels', 'other_labels')
    list_filter = ('effect_labels', 'time_labels', 'meat_labels', 'other_labels')
    inlines = (ComponentInline, ProcedureInline)
    readonly_fields = ('recipe_nutrition_list', 'macro_element_ratio', 'protein_ratio', 'fat_ratio', 'get_total_amount')

    def recipe_nutrition_list(self, instance):
        html = u'''
        <table>
        <thead><tr><th>营养物质</th><th>含量</th><th>单位</th></tr></thead>
        <tbody>
        '''
        row = 1
        for k, v in instance.get_nutrition().iteritems():
            html += u'<tr class="form-row row%s"><td>%s</td><td>%s</td><td>%s</td></tr>' % (row % 2, v['name'], str(v['amount']), v['unit'])
            row += 1
        return html + u'</tbody></table>'

    recipe_nutrition_list.short_description = u'菜谱营养表'
    recipe_nutrition_list.allow_tags = True


class IngredientAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'eng_name', 'ndbno')
    search_fields = ('name',)
    list_display_links = ('name',)
    inlines = (NutritionInline,)


admin.site.register(Recipe, RecipeAdmin)
admin.site.register(Ingredient, IngredientAdmin)
