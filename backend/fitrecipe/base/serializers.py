#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-18 23:06:06
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-19 00:27:44

from rest_framework import serializers


class BaseSerializer(serializers.ModelSerializer):

    def __init__(self, *args, **kwargs):
        # init 时传入一个可选参数 value，用于指定fields，如果不传则为 Meta 信息中指定的值
        self.value = kwargs.pop('value', None)
        return super(BaseSerializer, self).__init__(*args, **kwargs)

    def to_representation(self, obj):
        '''
        重写 to_representation 方法，这个方法会在解析data时被调用。

        其中的逻辑是西安调用父类的 to_representation，生成根据 fields 产生的 dict。如果没有 fields 则会使用所有的 key。
        然后在根据传入 value，去从父类的结果中拷贝出需要用到的 key。
        '''
        parent_result = super(BaseSerializer, self).to_representation(obj)  # 父类方法会判断 fields 是否有值，所以这里不用做判断了
        if self.value is None:
            # 没有传入 value，则直接返回父类结果
            return parent_result
        r = dict()
        for k in self.value:
            r[k] = parent_result[k]
        return r
