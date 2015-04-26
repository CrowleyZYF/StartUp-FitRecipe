#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-04-26 23:34:06
from django.http import Http404
from rest_framework.views import APIView


# Create your views here.
class BaseView(APIView):
    def get_object(self, model, pk):
        '''
        return instance of model whose pk is specified. 404 will be raised if instance not found.
        '''
        try:
            return model.objects.get(pk=pk)
        except model.DoesNotExist:
            raise Http404
