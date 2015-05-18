#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-08 20:28:07
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-18 17:57:33
from random import Random


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
