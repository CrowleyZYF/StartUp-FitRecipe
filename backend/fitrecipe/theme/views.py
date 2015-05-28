#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 13:16:10
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 15:57:45
from base.views import BaseView
from .serializers import ThemeSerializer
from .models import Theme


# Create your views here.
class ThemeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        reutrn a specific Theme
        '''
        theme = self.get_object(Theme, pk)
        serializer = ThemeSerializer(theme, context={'simple': False})
        return self.success_response(serializer.data)


class ThemeList(BaseView):
    def get(self, request, format=None):
        '''
        Theme List
        '''
        themes = Theme.objects.all()
        serializer = ThemeSerializer(themes, context={'simple': True}, many=True)
        return self.success_response(serializer.data)
