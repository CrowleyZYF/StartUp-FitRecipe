#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-18 23:06:06
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-19 00:10:27

from rest_framework import serializers
from django.db import models
import datetime


class BaseSerializer(serializers.ModelSerializer):

    def __init__(self, *args, **kwargs):
        # init 时传入一个可选参数 value，用于指定fields，如果不传则为 Meta 信息中指定的值
        self.value = kwargs.pop('value', None)
        return super(BaseSerializer, self).__init__(*args, **kwargs)

    def to_representation(self, obj):
        parent_result = super(BaseSerializer, self).to_representation(obj)
        if self.value is None:
            if self.fields is not None:
                self.value = self.fields  # 非空，就把 fields 赋值给 value
            else:
                return parent_result
        r = dict()
        for k in self.value:
            r[k] = parent_result[k]
        return r
        # # 自定义的解对象方法
        # for attribute_name in self.value:
        #     attribute = getattr(obj, attribute_name)
        #     if attribute_name.startswith('_'):
        #         # Ignore private attributes.
        #         pass
        #     elif hasattr(attribute, '__call__'):
        #         # Ignore methods and other callables.
        #         pass
        #     elif isinstance(attribute, (str, unicode, int, bool, float, type(None))):
        #         # Primitive types can be passed through unmodified.
        #         r[attribute_name] = attribute
        #     elif isinstance(attribute, list):
        #         # Recursively deal with items in lists.
        #         r[attribute_name] = [
        #             self.to_representation(item) for item in attribute
        #         ]
        #     elif isinstance(attribute, dict):
        #         # Recursively deal with items in dictionaries.
        #         r[attribute_name] = {str(key): self.to_representation(value) for key, value in attribute.items()}
        #     elif isinstance(attribute, models.Model):
        #         r[attribute_name] = self.to_representation(attribute)
        #     elif isinstance(attribute, (datetime.datetime)):
        #         r[attribute_name] = attribute.strftime('%Y-%m-%d %H-%M-%S')
        #     else:
        #         # Force anything else to its string representation.
        #         r[attribute_name] = str(attribute)
        # return r
