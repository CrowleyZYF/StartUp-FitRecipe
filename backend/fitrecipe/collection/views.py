#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from django.db import IntegrityError
from django.db.models import ObjectDoesNotExist

from base.views import BaseView, AuthView
from accounts.models import Account
from article.models import Series
from theme.models import Theme
from recipe.models import Recipe
from .models import ThemeCollection, SeriesCollection, RecipeCollection
from .serializers import RecipeCollectionSerializer, ThemeCollectionSerializer, SeriesCollectionSerializer


class CollectionCreate(AuthView):
    def post(self, request, format=None):
        try:
            data = json.loads(request.body)
            collection_id = data['id']
            collection_type = data['type']
        except:
            return self.fail_response(400, 'BadArgumrent')
        try:
            if collection_type == 'theme':
                model = ThemeCollection
                obj = Theme.objects.get(pk=collection_id)
                serializer = ThemeCollectionSerializer
            elif collection_type == 'series':
                model = SeriesCollection
                obj = Series.objects.get(pk=collection_id)
                serializer = SeriesCollectionSerializer
            elif collection_type == 'recipe':
                obj = Recipe.objects.get(pk=collection_id)
                model = RecipeCollection
                serializer = RecipeCollectionSerializer
            else:
                return self.fail_response(400, 'BadArgument')
        except ObjectDoesNotExist:
            return self.fail_response(400, 'DoesNotExist')
        obj.collection_count += 1
        obj.save()
        owner = Account.find_account_by_user(request.user)
        try:
            kwargs = {collection_type: obj, 'owner': owner}
            col = model.objects.get(**kwargs)
        except model.DoesNotExist:
            col = model()
            col.owner = owner
            setattr(col, collection_type, obj)
        col.status = 10
        col.save()
        return self.success_response(serializer(col).data)


class CollectionDelete(AuthView):
    def post(self, request, collection_type, collection_id, format=None):
        if collection_type == 'theme':
            model = ThemeCollection
            serializer = ThemeCollectionSerializer
        elif collection_type == 'series':
            model = SeriesCollection
            serializer = SeriesCollectionSerializer
        elif collection_type == 'recipe':
            model = RecipeCollection
            serializer = RecipeCollectionSerializer
        else:
            return self.fail_response(400, 'BadArgument')
        try:
            col = model.objects.get(pk=collection_id)
        except model.DoesNotExist:
            return self.fail_response(400, 'DoesNotExist')
        col.status = -10
        col.save()
        obj = getattr(col, collection_type)
        obj.collection_count -= 1
        obj.save()
        return self.success_response(serializer(col).data)


class CollectionDetail(AuthView):
    def get(self, request, collection_type, format=None):
        lastid = request.GET.get('lastid', None)
        if collection_type == 'theme':
            model = ThemeCollection
            serializer = ThemeCollectionSerializer
        elif collection_type == 'series':
            model = SeriesCollection
            serializer = SeriesCollectionSerializer
        elif collection_type == 'recipe':
            model = RecipeCollection
            serializer = RecipeCollectionSerializer
        else:
            return self.fail_response(400, 'BadArgument')
        owner = Account.find_account_by_user(request.user)
        if lastid:
            result = model.objects.filter(owner=owner, id__lt=lastid, status__gt=0).order_by('-created_time')[0:20]
        else:
            result = model.objects.filter(owner=owner, status__gt=0).order_by('-created_time')[0:20]
        return self.success_response(serializer(result, many=True).data)
