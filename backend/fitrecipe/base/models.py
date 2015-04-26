#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 14:45:00
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-04-26 14:51:43
from django.db import models


# Create your models here.
class Plain(models.Model):
    '''
    Plain abstract models class with nothing
    '''
    class Meta:
            abstract = True


class BaseModel(models.Model):
    '''
    Abstract models class with created_time and updated_time
    '''
    created_time = models.DateTimeField(auto_now_add=True)
    updated_time = models.DateTimeField(auto_now=True)

    class Meta:
            abstract = True
