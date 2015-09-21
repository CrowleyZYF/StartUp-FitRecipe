#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from django.db import IntegrityError
from base.views import BaseView
from .models import Plan, Calendar, Routine, Dish, SingleIngredient, SingleRecipe
from recipe.models import Recipe, Ingredient
from .serializers import PlanSerializer, CalendarSerializer
from accounts.models import Account
from datetime import date, datetime
# Create your views here.
class PlanList(BaseView):

    def get(self, request, format=None):
        '''
        List all plans in simple mode
        '''
        plans = Plan.objects.filter(is_personal=False)
        serializer = PlanSerializer(plans, many=True)
        return self.success_response(serializer.data)

    def post(self, request, format=None):
        '''
        create a personal plan
        {
            "dish": [{
                "type": 0,
                "ingredient": [{
                    "id": 1,
                    "amount": 100
                }],
                "recipe": [{
                    "id": 2,
                    "amount": 200
                }]
            },{
                "type": 1,
                "ingredient": [],
                "recipe": [{
                    "id":3,
                    "amount": 300
                }]
            }]
        }
        '''
        try:
            body = json.loads(request.body)
            user = Account.find_account_by_user(request.user)
            today = date.today()
            # get today first
            try:
                p = Plan.objects.get(user=user, authored_date=today)
                # exists
                # delete it
                p.delete()
            except Plan.DoesNotExist:
                # create new one
                pass
            p = Plan.objects.create(user=user)
            r = Routine.objects.create(plan=p)
            for dish in body.get('dish', []):
                d = Dish.objects.create(type=dish['type'], routine=r)
                for si in dish.get('ingredient', []):
                    ingredient = Ingredient.objects.get(pk=si['id'])
                    SingleIngredient.objects.create(ingredient=ingredient, amount=si['amount'], dish=d)
                for sr in dish.get('recipe', []):
                    recipe = Recipe.objects.get(pk=sr['id'])
                    SingleRecipe.objects.create(recipe=recipe, amount=sr['amount'], dish=d)
            # after created it join it!
            try:
                c = Calendar.objects.get(user=user, joined_date=today)
                if c.plan.id != p.id:
                    c.plan = p
                    c.save()
            except Calendar.DoesNotExist:
                Calendar.objects.create(user=user, plan=p)
            return self.success_response('ok')
        except:
            raise#return self.fail_response(400, 'fail')


class PlanDetail(BaseView):

    def get(self, request, plan_id, format=None):
        '''
        get detail info
        '''
        plan = Plan.objects.get(pk=plan_id)
        serializer = PlanSerializer(plan, context={'simple': False})
        return self.success_response(serializer.data)


class CalendarList(BaseView):

    def get(self, request, format=None):
        '''
        get my calendar
        '''
        user = Account.find_account_by_user(request.user)
        turn_to_date = lambda x: x and datetime.strptime(x, '%Y%m%d') or date.today()
        start_date = turn_to_date(request.GET.get('start', None))
        end_date = turn_to_date(request.GET.get('end', None))
        calendars = Calendar.objects.filter(user=user, joined_date__gte=start_date, joined_date__lte=end_date)
        serializer = CalendarSerializer(calendars, many=True)
        return self.success_response(serializer.data)

    def post(self, request, format=None):
        '''
        join plan
        same date & user will use the last one
        '''
        body = json.loads(request.body)
        plan_id = body.get('plan', None)
        user = Account.find_account_by_user(request.user)
        if plan_id is None:
            return self.fail_response(400, 'No Plan')
        try:
            p = Plan.objects.get(pk=plan_id)
        except Plan.DoesNotExist:
            return self.fail_response(400, 'Plan Not Exist')
        try:
            c = Calendar.objects.create(user=user, plan=p)
            return self.success_response('ok')
        except IntegrityError:
            # existed today
            today = date.today()
            c = Calendar.objects.get(user=user, joined_date=today)
            c.plan = p
            c.save()
            return self.success_response('ok')
        except:
            return self.fail_response(400, 'error')

