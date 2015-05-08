#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 14:50:49
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-08 19:11:48
from django.db import models
from django.contrib.auth.models import User


# Create your models here.
class Account(User):
    '''
    User Account

    id 用户 id
    username 用户名，如果是手动注册则自动生成一个，如果是第三方，则使用第三方用户名
    password 密码
    phone 注册用的手机号，唯一
    external_id 第三方渠道 id
    external_source 第三方渠道
    (external_id, external_source) 一起 唯一
    '''
    phone = models.CharField(max_length=30, null=True, unique=True)  # do not add blank=True, because two blanks will be judged as the same value
    is_changed_nick = models.BooleanField(default=False)
    external_id = models.CharField(max_length=100, null=True)
    external_source = models.CharField(max_length=10, null=True)

    class Meta:
        unique_together = ('external_id', 'external_source',)
