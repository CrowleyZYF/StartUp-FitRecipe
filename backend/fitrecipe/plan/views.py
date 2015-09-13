#!/usr/bin/env python
# -*- coding: utf-8 -*-

from base.views import BaseView
from .models import Plan
from .serializers import PlanSerializer

# Create your views here.
class PlanList(BaseView):

    def get(self, request, format=None):
        '''
        List all plans in simple mode
        '''
        plans = Plan.objects.all()
        serializer = PlanSerializer(plans, many=True)
        return self.success_response(serializer.data)


class PlanDetail(BaseView):

    def get(self, request, plan_id, format=None):
        '''
        get detail info
        '''
        plan = Plan.objects.get(pk=plan_id)
        serializer = PlanSerializer(plan, context={'simple': False})
        return self.success_response(serializer.data)
