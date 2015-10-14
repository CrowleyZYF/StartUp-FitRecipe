#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-08 20:28:07
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-25 17:52:50
from random import Random
# from recipe.serializers import ComponentSerializer


def random_str(scope=None, num=16):
        str = ''
        if scope == 'number':
            chars = '1234567890'
        elif scope == 'alphabet':
            chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz'
        else:
            chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'
        length = len(chars) - 1
        random = Random()
        for i in range(num):
            str += chars[random.randint(0, length)]
        return str


def split_labels_into_list(labels):
        tmp = list()
        for v in labels.split(','):
            try:
                tmp.append(int(v))  # 转数字
            except (TypeError, ValueError):
                continue
        return tmp


def str_to_int(str, default=None):
    '''
    转换字符串为数字

    str: 123 (string) - 需要转换的字符差u你
    default: 1 (number) - 如果异常，返回的值。若没有 defalut 并且产生了异常，会抛出异常
    '''
    try:
        return int(str)
    except:
        if default is None:
            raise
        else:
            return default

def pick_data(objs, type):
    result = []
    for item in objs:
        result.append({
            'id': item.id,
            'name': type == 0 and item.name or item.title,
            'type': type,
            'nutrition_set': item.get_nutrition(),
            #'component_set': type == 0 and '' or ComponentSerializer(item.component_set.all(), many=True).data,
            'component_set': type == 1 and item.get_component() or [],
            'amount': type == 1 and item.get_total_amount(True) or 100.0,
            })
    return result
