#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:30:44
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 17:22:57
from django.conf import settings
from django.db import models

from accounts.models import OtherAuthor
from base.models import BaseModel
from label.models import Label
from fitrecipe.utils import split_labels_into_list, str_to_int


class Recipe(BaseModel):
    '''
    recipe models
    '''
    # 菜谱id号(auto)，菜谱封面url，菜谱名称，功效，烹饪时间，卡路里，收藏数(redis)
    author = models.ForeignKey(OtherAuthor, null=True, blank=True) # 加上可空是为了迁移
    img = models.URLField(max_length=200, verbose_name=u'大图 URL')  # 图片全部使用 CDN
    thumbnail = models.URLField(max_length=200, verbose_name=u'缩略图 URL')
    recommend_img = models.URLField(max_length=200, null=True, blank=True, verbose_name=u'推荐大图 URL')
    recommend_thumbnail = models.URLField(max_length=200, null=True, blank=True, verbose_name=u'推荐缩略图 URL')
    title = models.CharField(max_length=100, verbose_name=u'菜谱名称')
    introduce = models.TextField(null=True, blank=True, verbose_name=u'菜谱简介')
    tips = models.TextField(null=True, blank=True, verbose_name=u'菜谱小贴士')
    duration = models.IntegerField(help_text=u'分钟', verbose_name=u'烹饪时间')  # 烹饪时间
    effect_labels = models.ManyToManyField(Label, limit_choices_to={u'type': u'功效'}, related_name='effect_set', verbose_name=u'功效标签')
    time_labels = models.ManyToManyField(Label, limit_choices_to={u'type': u'用餐时间'}, related_name='time_set', verbose_name=u'用餐时间标签')
    meat_labels = models.ManyToManyField(Label, limit_choices_to={u'type': u'食材'}, related_name='meat_set', verbose_name=u'食材标签')
    other_labels = models.ManyToManyField(Label, limit_choices_to={u'type': u'其他'}, related_name='other_set', verbose_name=u'其他标签')
    calories = models.FloatField(default=0, help_text=u'自动计算，不用填', verbose_name=u'每百克卡路里')
    collection_count = models.IntegerField(default=0, verbose_name=u'收藏数')
    status = models.IntegerField(default=-10, help_text=u'删除：-20，草稿: -10，正常：10', verbose_name=u'状态')

    class Meta:
        verbose_name = u'菜谱'
        verbose_name_plural = u'%s表' % verbose_name

    def __unicode__(self):
        return self.title

    def get_latest_comment(self):
        return self.comment_set.order_by('-created_time')[0:20]

    def get_total_amount(self, float_format=False):
        total_amount = 0.0
        for item in self.component_set.all():
            total_amount += item.amount
        if float_format:
            return total_amount
        else:
            return '%dg' % round(total_amount, 2)

    def get_nutrition(self):
        r = dict()
        total_amount = 0.0
        for item in self.component_set.all():
            c_amount = item.amount
            total_amount += c_amount
            for n in item.ingredient.nutrition_set.all():
                if n.eng_name in r.keys():
                    r[n.eng_name]['amount'] += n.amount * c_amount
                else:
                    r[n.eng_name] = {'amount': n.amount * c_amount, 'unit': n.unit, 'name': n.name}
        for k, v in r.iteritems():
            v['amount'] = round(v['amount'] / total_amount, 2)  # 一百克含量
        return r

    def get_component(self):
        c = list()
        for item in self.component_set.all():
            temp = dict()
            ingredient = dict()
            ingredient['id'] = item.ingredient.id
            ingredient['name'] = item.ingredient.name            
            temp['ingredient'] = ingredient
            temp['amount'] = item.amount
            temp['remark'] = item.remark
            c.append(temp)
        return c
            

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

    def macro_element_ratio(self, list_format=False):
        data = self.get_nutrition()
        if data:
            transfer_100_int = lambda x: int(self.get_nutrition_amount(data, x) * 100)
            ratio = (
                self.get_nutrition_amount(data,u'Carbohydrate, by difference'),
                self.get_nutrition_amount(data, u'Protein'),
                self.get_nutrition_amount(data, u'Total lipid (fat)')
                )
            if sum(ratio) == 0:
                ratio = [0, 0, 0]
            else:
                ratio = [int(v / sum(ratio) * 100) for v in ratio]
                first_gcd = self.gcd(ratio[0], ratio[1])
                second_gcd = self.gcd(ratio[1], ratio[2])
                third_gcd = self.gcd(first_gcd, second_gcd)
                ratio = [num/third_gcd for num in ratio]
            if list_format:
                return ratio
            else:
                return u'%d:%d:%d' % tuple(ratio)
        else:
            if list_format:
                return None
            else:
                return u'no data';

    def protein_ratio(self):
        '''
        蛋白质在宏量元素比中占比
        '''
        ratio = self.macro_element_ratio(list_format=True)
        try:
            return u'%.2f%%' % (float(ratio[1])/ sum(ratio) * 100)
        except (ZeroDivisionError, TypeError):
            return u'0%'

    def fat_ratio(self):
        '''
        脂类在宏量元素比中占比
        '''
        ratio = self.macro_element_ratio(list_format=True)
        try:
            return u'%.2f%%' % (float(ratio[2])/ sum(ratio) * 100)
        except (ZeroDivisionError, TypeError):
            return u'0%'

    def update_calories(self):
        # 对于删除所有配料的情况需要有特殊处理，因为最后一个删除的时候，不会进入循环，因此会留下最后一个配料的卡路里
        if self.component_set.count() == 0:
            self.calories = 0
            self.save()
            return True
        for k, v in self.get_nutrition().iteritems():
            if k == settings.CALORIES_CN_NAME:
                self.calories = v['amount']
                self.save()
                break
        return True

    # django admin uses these attribute
    macro_element_ratio.short_description = u'宏量元素比'
    protein_ratio.short_description = u'蛋白质占比'
    fat_ratio.short_description = u'脂类占比'
    get_total_amount.short_description = u'总重量'

    @classmethod
    def get_recipe_list(cls, meat_labels, effect_labels, time_labels, order, desc, start, num):
        '''
        根据条件获取菜谱列表

        meat_labels: 4,5 (string) - label ID 使用逗号连接
        effect_labels: 1,2 (string) - 同上
        time_labels: 3 (string) - 同上
        order: calories (string) - 排序方式，默认是 calories，其他可选值：duration（烹饪时间），收藏数（暂不支持）
        desc: false (string) - 是否倒序，默认是字符串 false，还可以是字符串 true
        start: 0 (string) - 偏移，默认是0。都是字符串，函数里会做转换，转成数字
        num: 10 (string) - 返回数量，默认是10。字符串
        '''
        recipes = cls.objects.all()
        query = {'status__gt': 0}
        # 逗号里面的是或 是并集 出现一个就好
        if meat_labels is not None:
            # 对 食材 进行筛选
            query['meat_labels__id__in']=split_labels_into_list(meat_labels)
        if effect_labels is not None:
            query['effect_labels__id__in']=split_labels_into_list(effect_labels)
        if time_labels is not None:
            query['time_labels__id__in']=split_labels_into_list(time_labels)
        if query:
            print query
            recipes = recipes.filter(**query);
            print recipes
        if order == 'duration':
            recipes = recipes.order_by('duration')
        else:
            # 还有按照收藏数的排序，不过现在还没做
            # 默认按照卡路里
            recipes = recipes.order_by('calories')
        if desc == 'true':
            recipes = recipes.reverse()
        start = str_to_int(start, 0)
        num = str_to_int(num, 10)
        # bug in django 1.8.3, filter duplicate
        recipes = recipes.distinct()
        if num < 1:
            # 如果请求num数量小于 0 是不对的，改成 10
            num = 10
        if start < 0:
            # 如果 start 是负数，改成 0
            start = 0
        return recipes[start:num + start]

    @classmethod
    def get_latest_recipe(cls):
        return cls.objects.filter(status__gt=0).order_by('-updated_time')[0:10]


class Component(BaseModel):
    '''
    菜谱的食材构成
    '''
    recipe = models.ForeignKey('Recipe')
    ingredient = models.ForeignKey('Ingredient')
    amount = models.IntegerField()  # 克数 没有小数
    remark = models.CharField(max_length=100, null=True, blank=True)

    class Meta:
        verbose_name = '构成'
        verbose_name_plural = verbose_name + '表'

    def __unicode__(self):
        return u'%s 的食材【%s】' % (self.recipe.title, self.ingredient.name)

    def save(self, *args, **kwargs):
        # 捕捉 save 事件，计算卡路里，并写入字段 calories 中用于以后排序
        # 先保存一次，不然计算卡路里的时候会使用以前的配料表
        super(Component, self).save(*args, **kwargs)
        self.recipe.update_calories()

    def delete(self, *args, **kwargs):
        # 删除事件也要捕捉，删除的时候不会调用 save
        super(Component, self).delete(*args, **kwargs)
        self.recipe.update_calories()


class Procedure(BaseModel):
    '''
    操作步骤
    '''
    recipe = models.ForeignKey('Recipe')
    num = models.IntegerField()  # 第num步，应该和 recipe 一起作为 unique
    content = models.TextField()
    img = models.URLField(null=True, blank=True)

    class Meta:
        # unique_together = (('recipe', 'num'),)  # 有必要嘛？是不是太强制了一点？
        verbose_name = '步骤'
        verbose_name_plural = verbose_name + '表'
        # ordering = ['-recipe', 'num']

    def __unicode__(self):
        return u'%s的步骤 第%s步 ' % (self.recipe.title, self.num)


class Ingredient(BaseModel):
    name = models.CharField(max_length=25)
    eng_name = models.CharField(max_length=100, null=True, blank=True)  # 营养网站上的英文名名字，自动填入
    ndbno = models.CharField(max_length=25)  # 营养网站上的id, 要保留前面的0呐
    # nutrition_set can get all nutritions

    class Meta:
        verbose_name = '原料'
        verbose_name_plural = verbose_name + '表'

    def __unicode__(self):
        return self.name

    def get_nutrition(self):
        r = dict()
        for n in self.nutrition_set.all():
            r[n.eng_name] = {'amount': n.amount , 'unit': n.unit, 'name': n.name}
        return r

    def save(self, *args, **kwargs):
        '''
        捕捉save事件，通过英文名，去录入营养物质
        '''
        import requests
        import json
        # 先保存，不然后面外键指不到吧
        url = u'http://%s/usda/ndb/reports/?ndbno=%s&type=f&format=json&api_key=%s' % (settings.NDB_IP, self.ndbno, settings.NDB_API_KEY)
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
                try:
                    if isinstance(item[0], tuple):
                        # 不饱和脂肪酸要求和
                        for sid in item[0]:
                            total += nutri_dict[sid][0]
                            unit = nutri_dict[sid][1]
                    else:
                        total = nutri_dict[item[0]][0]
                        unit = nutri_dict[item[0]][1]
                except KeyError:
                    # key 不存在
                    total = 0.0
                    unit = u'mg'
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

    class Meta:
        verbose_name = '营养'
        verbose_name_plural = verbose_name + '表'

    def __unicode__(self):
        return u'%s 的营养 【%s】' % (self.ingredient.name, self.name)
