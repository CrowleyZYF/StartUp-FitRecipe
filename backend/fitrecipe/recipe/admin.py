#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-10 23:37:51
from django.contrib import admin
from django.utils.html import format_html_join
from django.utils.safestring import mark_safe

from .models import Recipe, Component, Ingredient, Nutrition, Procedure
# Register your models here.


class ComponentInline(admin.TabularInline):
    model = Component


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
    readonly_fields = ('recipe_nutrition_list', 'macro_element_ratio')

    def recipe_nutrition_list(self, instance):
        html = u'''
        <table>
        <thead><tr><th>营养物质</th><th>含量</th><th>单位</th></tr></thead>
        <tbody>
        '''
        row = 1
        for k, v in instance.get_nutrition().iteritems():
            html += u'<tr class="form-row row%s"><td>%s</td><td>%s</td><td>%s</td></tr>' % (row % 2, k, str(v['amount']), v['unit'])
            row += 1
        return html + u'</tbody></table>'

    def get_nutrition_amount(self, data, name):
        '''
        g,mg,ug 的转换为g
        '''
        if data[name]['unit'] == u'mg':
            return data[name]['amount'] / 1000
        elif data[name]['unit'] == u'g':
            return data[name]['amount']
        elif data[name]['unit'] == u'μg':
            return data[name]['amount'] / 1000000
        else:
            return data[name]['amount']

    def gcd(self, a, b):
        if a < b:
            a, b = b, a
        while b != 0:
            temp = a % b
            a = b
            b = temp
        return a

    def macro_element_ratio(self, instance):
        data = instance.get_nutrition()
        transfer_100_int = lambda x: int(self.get_nutrition_amount(data, x) * 100)
        ratio = (transfer_100_int(u'碳水化合物'), transfer_100_int(u'蛋白质'), transfer_100_int(u'脂类'))
        first_gcd = self.gcd(ratio[0], ratio[1])
        second_gcd = self.gcd(ratio[1], ratio[2])
        third_gcd = self.gcd(first_gcd, second_gcd)
        return '%s:%s:%s' % tuple([num/third_gcd for num in ratio])


    recipe_nutrition_list.short_description = u'菜谱营养表'
    recipe_nutrition_list.allow_tags = True
    macro_element_ratio.short_description = u'宏量元素比'


class IngredientAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'eng_name', 'ndbno')
    search_fields = ('name',)
    list_display_links = ('name',)
    inlines = (NutritionInline,)



admin.site.register(Recipe, RecipeAdmin)
admin.site.register(Ingredient, IngredientAdmin)
