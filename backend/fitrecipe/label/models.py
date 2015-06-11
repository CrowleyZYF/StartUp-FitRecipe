#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-20 18:10:00
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-06-10 23:18:43
from django.db import models
from base.models import BaseModel


# Create your models here.
class Label(BaseModel):
    '''
    标签 (功效) 增肌 减脂 (用餐时间) 早餐 加餐 正餐 (食材) 鸡肉 鱼肉 牛肉 海鲜 蛋奶 果蔬 米面 点心 (其他标签) 酸甜 等等
    '''
    name = models.CharField(max_length=25, verbose_name=u'标签名称')
    type = models.CharField(max_length=25, choices=((u'功效', u'功效'), (u'用餐时间', u'用餐时间'), (u'食材', u'食材'), (u'其他', u'其他'),), verbose_name=u'标签类型')

    class Meta:
        verbose_name = u'标签'
        verbose_name_plural = verbose_name + u'表'

    def __unicode__(self):
        return self.name

    @classmethod
    def get_label_in_group(cls):
        '''
        获取 label，根据类型。

        返回一个 dict，类型是 key
        '''
        r = dict()
        for l in cls.objects.all():
            if l.type in r.keys():
                r[l.type].append(l)
            else:
                r[l.type] = [l, ]
        return r
