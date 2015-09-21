#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-04 14:50:49
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-07-10 22:50:07
from django.db import models
from django.contrib.auth.models import User

from base.models import BaseModel

from django.core.validators import MinValueValidator, MaxValueValidator

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
    avatar = models.URLField(max_length=200, default='http://tp2.sinaimg.cn/1937464505/180/5708528601/1')
    nick_name = models.CharField(max_length=100)
    is_changed_nick = models.BooleanField(default=False)
    is_official = models.BooleanField(default=False)

    def __unicode__(self):
        return u'%s_%s' % (self.id, self.phone or self.nick_name)

    @classmethod
    def find_account_by_user(cls, user):
        return cls.objects.get(pk=user.id)

class External(BaseModel):
    '''
    第三方登录
    '''
    user = models.ForeignKey(Account, related_name='externals')
    external_id = models.CharField(max_length=100)
    external_source = models.CharField(max_length=10)

    class Meta:
        unique_together = ('external_id', 'external_source',)

    def __unicode__(self):
        return u'%s_%s' % (self.external_source, self.external_id)


class OtherAuthor(BaseModel):
    '''
    非注册用户，用于菜谱的作者等
    '''
    avatar = models.URLField(max_length=200, default='http://tp2.sinaimg.cn/1937464505/180/5708528601/1')
    nick_name = models.CharField(max_length=100)
    sex = models.CharField(max_length=10)

    class Meta:
        verbose_name = u'非注册用户'
        verbose_name_plural = u'%s表' % verbose_name

    def __unicode__(self):
        return u'Other_%s' % self.nick_name

class Evaluation(BaseModel):
    '''
    用户评测报告
    '''

    user = models.ForeignKey(Account, on_delete=models.CASCADE, verbose_name=u'用户')
    #0 for male and 1 for female
    gender = models.IntegerField(verbose_name=u'性别', validators=[MinValueValidator(0), MaxValueValidator(1)])
    age = models.IntegerField(verbose_name=u'年龄', validators=MinValueValidator(5))
    #身高单位为cm
    height = models.PositiveIntegerField(verbose_name=u'身高')
    weight = models.FloatField(verbose_name=u'体重', validators=MinValueValidator(0.0))
    #int, 0~4
    roughFat = models.IntegerField(verbose_name=u'粗略体脂', validators=[MinValueValidator(0), MaxValueValidator(4)])
    preciseFat = models.FloatField(verbose_name=u'精确体脂', validators=MinValueValidator(0.0))
    #int, 0~3
    jobType = models.IntegerField(verbose_name=u'工作强度', validators=[MinValueValidator(0), MaxValueValidator(3)])
    #int, 0增肌， 1减脂
    goalType = models.IntegerField(verbose_name=u'目标', validators=[MinValueValidator(0), MaxValueValidator(1)])
    exerciseFrequency = models.IntegerField(verbose_name=u'每周运动次数')
    exerciseInterval = models.IntegerField(verbose_name=u'运动时长', validators=[MinValueValidator(0), MaxValueValidator(3)])
    weightGoal = models.FloatField(verbose_name=u'目标体重', validators=MinValueValidator(0.0))
    daysToGoal = models.PositiveIntegerField(verbose_name=u'时间')

    class Meta:
        verbose_name = u'评测报告'
        verbose_name_plural = u'%s表' % verbose_name

    def __unicode__(self):
        return u'%s\'s evalutaion' % self.user.phone or self.user.nick_name

