#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from django.db import IntegrityError
from base.views import BaseView
from .models import Plan, Calendar, Routine, Dish, SingleIngredient, SingleRecipe, Punch
from recipe.models import Recipe, Ingredient
from .serializers import PlanSerializer, CalendarSerializer, PunchSerializer, DishSerializer
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
            "id": 18,
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
            }],
            "joined_date": "2015-01-01"
        }
        '''
        try:
            body = json.loads(request.body)
            user = Account.find_account_by_user(request.user)
            try:
                joined_date = datetime.strptime(body['joined_date'],'%Y-%m-%d').date()
            except:
                joined_date = date.today()
            planid = body.get('id', None)
            if planid is None:
                # create
                p = Plan.objects.create(user=user)
            else :
                p = Plan.objects.get(user=user, pk=planid)
                p.delete_routines()
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
                c = Calendar.objects.get(user=user, joined_date=joined_date)
                if c.plan.id != p.id:
                    c.plan = p
                    c.save()
            except Calendar.DoesNotExist:
                Calendar.objects.create(user=user, plan=p, joined_date=joined_date)
            return self.success_response(PlanSerializer(p).data)
        except:
            return self.fail_response(400, 'fail')


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
        try:
            last_joined = Calendar.objects.filter(user=user, joined_date__lte=start_date).order_by('-joined_date')[0]
            last = CalendarSerializer(last_joined).data
        except IndexError:
            last = None
        serializer = CalendarSerializer(calendars, many=True).data
        punchs = Punch.objects.filter(user=user, date__lte=end_date, date__gte=start_date)
        result = {'lastJoined': last, 'calendar': serializer, 'punch': PunchSerializer(punchs, many=True).data}
        return self.success_response(result)

    def post(self, request, format=None):
        '''
        join plan
        same date & user will use the last one
        {
            "plan": 2,
            "joined_date": "2015-01-01"
        }
        '''
        body = json.loads(request.body)
        plan_id = body.get('plan', None)
        try:
            joined_date = datetime.strptime(body['authored_date'],'%Y-%m-%d').date()
        except:
            joined_date = date.today()
        user = Account.find_account_by_user(request.user)
        if plan_id is None:
            return self.fail_response(400, 'No Plan')
        try:
            p = Plan.objects.get(pk=plan_id)
            p.dish_headcount += 1
            p.save()
        except Plan.DoesNotExist:
            return self.fail_response(400, 'Plan Not Exist')
        try:
            c = Calendar.objects.create(user=user, plan=p, joined_date=joined_date)
            return self.success_response('ok')
        except IntegrityError:
            # existed today
            c = Calendar.objects.get(user=user, joined_date=joined_date)
            c.plan = p
            c.save()
            return self.success_response('ok')
        except:
            return self.fail_response(400, 'error')

class LastPlan(BaseView):
    '''
    获取最后一个
    '''
    def get(self, request, format=None):
        user = Account.find_account_by_user(request.user)
        c = Calendar.objects.filter(user=user, joined_date__lte=date.today()).order_by('-joined_date')[0]
        return self.success_response(CalendarSerializer(c).data)


class PunchList(BaseView):
    '''
    打卡
    '''
    def get(self, request, format=None):
        user = Account.find_account_by_user(request.user)
        turn_to_date = lambda x: x and datetime.strptime(x, '%Y%m%d') or date.today()
        start_date = turn_to_date(request.GET.get('start', None))
        end_date = turn_to_date(request.GET.get('end', None))
        punchs = Punch.objects.filter(user=user, date__lte=end_date, date__gte=start_date)
        '''Temp'''
        result = []
        user = Account.find_account_by_user(request.user)
        c = Calendar.objects.filter(user=user, joined_date__lte=date.today()).order_by('-joined_date')[0]
        for punch_item in punchs:
            if punch_item.date==c.joined_date:                
                plan = Plan.objects.get(pk=c.plan.id)
                dish = plan.routine_set.get(day=1).dish_set.get(type=punch_item.type)
                result.append({'dish':DishSerializer(dish).data})
        #return self.success_response(PunchSerializer(punchs, many=True).data)
        return self.success_response(result)

    def post(self, request, format=None):
        user = Account.find_account_by_user(request.user)
        body = json.loads(request.body)
        type = body.get('type', None)
        img = body.get('img', None)
        if None in (type, img):
            return self.fail_response(400, 'Wrong Data')
        p = Punch.objects.create(user=user, type=type, img=img)
        return self.success_response(PunchSerializer(p).data)
