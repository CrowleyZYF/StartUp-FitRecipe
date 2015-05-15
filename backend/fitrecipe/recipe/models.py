#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-15 19:11:24
from django.db import models

from base.models import BaseModel


class Recipe(BaseModel):
    '''
    recipe models
    '''
    # 菜谱id号(auto)，菜谱封面url，菜谱名称，功效，烹饪时间，卡路里，收藏数(redis)
    img = models.URLField(max_length=200)  # 图片全部使用 CDN
    thumbnail = models.URLField(max_length=200)
    title = models.CharField(max_length=100)
    duration = models.IntegerField()  # 烹饪时间
    effect_labels = models.ManyToManyField('Label', limit_choices_to={'type': '功效'}, related_name='effect_set', null=True, blank=True)
    time_labels = models.ManyToManyField('Label', limit_choices_to={'type': '用餐时间'}, related_name='time_set', null=True, blank=True)
    meat_labels = models.ManyToManyField('Label', limit_choices_to={'type': '食材'}, related_name='meat_set', null=True, blank=True)
    other_labels = models.ManyToManyField('Label', limit_choices_to={'type': '其他'}, related_name='other_set', null=True, blank=True)

    def __unicode__(self):
        return self.title

    def get_nutrition(self):
        r = dict()
        for item in self.component_set.all():
            c_amount = item.amount
            for n in item.ingredient.nutrition_set.all():
                if n.id in r.keys():
                    r[n.id]["amount"] += round(n.amount/100*c_amount, 2)
                else:
                    r[n.id] = {"amount": round(n.amount/100*c_amount, 2), "unit": n.unit, "name": n.name}
        return r


class Component(BaseModel):
    '''
    菜谱的食材构成
    '''
    recipe = models.ForeignKey('Recipe')
    ingredient = models.ForeignKey('Ingredient')
    amount = models.IntegerField()  # 克数 没有小数
    remark = models.CharField(max_length=100, null=True, blank=True)

    def __unicode__(self):
        return u'%s 的食材【%s】' % (self.recipe.title, self.ingredient.name)


class Procedure(BaseModel):
    '''
    操作步骤
    '''
    recipe = models.ForeignKey('Recipe')
    num = models.IntegerField()  # 第num步，应该和 recipe 一起作为 unique
    content = models.TextField()
    img = models.URLField(null=True, blank=True)

    class Meta:
        unique_together = (('recipe', 'num'),)  # 有必要嘛？是不是太强制了一点？
        # ordering = ['-recipe', 'num']

    def __unicode__(self):
        return u'%s的步骤 第%s步 ' % (self.recipe.title, self.num)


class Label(BaseModel):
    '''
    标签 (功效) 增肌 减脂 (用餐时间) 早餐 加餐 正餐 (食材) 鸡肉 鱼肉 牛肉 海鲜 蛋奶 果蔬 米面 点心 (其他标签) 酸甜 等等
    '''
    name = models.CharField(max_length=25)
    type = models.CharField(max_length=25)

    def __unicode__(self):
        return self.name


class Ingredient(BaseModel):
    name = models.CharField(max_length=25)
    eng_name = models.CharField(max_length=100, null=True, blank=True)  # 营养网站上的英文名名字，自动填入
    ndbno = models.CharField(max_length=25)  # 营养网站上的id, 要保留前面的0呐
    # nutrition_set can get all nutritions

    def __unicode__(self):
        return self.name

    def save(self, *args, **kwargs):
        '''
        捕捉save事件，通过英文名，去录入营养物质
        '''
        import requests
        import json
        from django.conf import settings
        # 先保存，不然后面外键指不到吧
        url = u'http://api.nal.usda.gov/usda/ndb/reports/?ndbno=%s&type=f&format=json&api_key=%s' % (self.ndbno, settings.NDB_API_KEY)
        resp = requests.get(url)
        if resp.status_code == 200:
            content = json.loads(resp.content)['report']['food']
            self.eng_name = content['name']
            # 在这里执行保存操作，不然后面外键指不到了。
            r = super(Ingredient, self).save(*args, **kwargs)
            # 保存完之后开始处理 nutritions
            nutri_dict = dict()
            for nu in content['nutrients']:
                # loop nutritions, 把他转成id做key的dict，不然查起来太费劲了
                nutri_dict[nu['nutrient_id']] = (nu['value'], nu['unit'])
            # 先把表里的营养都删掉，不然要重复
            Nutrition.objects.filter(ingredient=self).delete()
            for item in settings.NDB_NUTRITION_ID_LIST:
                total = 0.0
                unit = ''
                if isinstance(item[0], tuple):
                    # 不饱和脂肪酸要求和
                    for sid in item[0]:
                        total += nutri_dict[sid][0]
                        unit = nutri_dict[sid][1]
                else:
                    total = nutri_dict[item[0]][0]
                    unit = nutri_dict[item[0]][1]
                Nutrition.objects.create(name=item[2], eng_name=item[1], amount=total, unit=unit, ingredient=self)
            return r
        elif resp.status_code == 400:
            raise AssertionError('400, check ndbno, dont forget the 0 before it')
        else:
            raise AssertionError('remote website said: %s' % resp.status_code)


class Nutrition(BaseModel):
    name = models.CharField(max_length=25)
    eng_name = models.CharField(max_length=100)
    amount = models.FloatField()
    unit = models.CharField(max_length=25)
    ingredient = models.ForeignKey('Ingredient')

    def __unicode__(self):
        return u'%s 的营养 【%s】' % (self.ingredient.name, self.name)
