#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 14:50:49
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-09 13:25:51
from django.db import models
from django.contrib.auth.models import User

from base.models import BaseModel


# Create your models here.
class Account(User):
    '''
    User Account

    id 用户 id
    username 用户名，如果是手动注册则自动生成一个，如果是第三方，则使用第三方用户名
    password 密码
    phone 注册用的手机号，唯一
    '''
    phone = models.CharField(max_length=30, null=True, unique=True)
    nick_name = models.CharField(max_length=100)
    is_changed_nick = models.BooleanField(default=False)


class External(BaseModel):
    '''
    第三方登录
    '''
    user = models.ForeignKey(Account, related_name='externals')
    external_id = models.CharField(max_length=100)
    external_source = models.CharField(max_length=10)

    class Meta:
        unique_together = ('external_id', 'external_source',)
