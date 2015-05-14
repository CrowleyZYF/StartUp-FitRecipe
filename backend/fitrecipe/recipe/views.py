#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-04-26 15:44:45
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-13 20:38:57

from base.views import BaseView
from .models import Recipe, Component, Ingredient, Label, Nutrition, Procedure
from .serializers import RecipeSerializer, ComponentSerializer, LabelSerializer, IngredientSerializer, NutritionSerializer, ProcedureSerializer
# Create your views here.


class RecipeList(BaseView):
    def get(self, request, format=None):
        '''
        List all recipes
        '''
        recipes = Recipe.objects.all()
        serializer = RecipeSerializer(recipes, many=True)
        return self.success_response(serializer.data)


class RecipeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific recipe.
        '''
        recipe = self.get_object(Recipe, pk)
        serializer = RecipeSerializer(recipe)
        return self.success_response(serializer.data)


class ComponentList(BaseView):
    def get(self, request, format=None):
        '''
        List all Components
        '''
        components = Component.objects.all()
        serializer = ComponentSerializer(components, many=True)
        return self.success_response(serializer.data)


class ComponentDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific component.
        '''
        component = self.get_object(Component, pk)
        serializer = ComponentSerializer(component)
        return self.success_response(serializer.data)


class IngredientList(BaseView):
    def get(self, request, format=None):
        '''
        List all Ingredients
        '''
        ingredients = Ingredient.objects.all()
        serializer = IngredientSerializer(ingredients, many=True)
        return self.success_response(serializer.data)


class IngredientDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific Ingredient.
        '''
        ingredient = self.get_object(Ingredient, pk)
        serializer = IngredientSerializer(ingredient)
        return self.success_response(serializer.data)


class LabelList(BaseView):
    def get(self, request, format=None):
        '''
        List all Labels
        '''
        labels = Label.objects.all()
        serializer = LabelSerializer(labels, many=True)
        return self.success_response(serializer.data)


class LabelDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific Label.
        '''
        label = self.get_object(Label, pk)
        serializer = LabelSerializer(label)
        return self.success_response(serializer.data)


class NutritionList(BaseView):
    def get(self, request, format=None):
        '''
        List all Nutritions
        '''
        nutritions = Nutrition.objects.all()
        serializer = NutritionSerializer(nutritions, many=True)
        return self.success_response(serializer.data)


class NutritionDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific Nutrition.
        '''
        nutrition = self.get_object(Nutrition, pk)
        serializer = NutritionSerializer(nutrition)
        return self.success_response(serializer.data)


class ProcedureList(BaseView):
    def get(self, request, format=None):
        '''
        List all Procedures
        '''
        procedures = Procedure.objects.all()
        serializer = ProcedureSerializer(procedures, many=True)
        return self.success_response(serializer.data)


class ProcedureDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific Procedure.
        '''
        procedure = self.get_object(Procedure, pk)
        serializer = ProcedureSerializer(procedure)
        return self.success_response(serializer.data)


class RecipeRecommand(BaseView):
    def get(self, request, pk, format=None):
        '''
        return three recommand recipes list

        1. 首页推荐内容
        2. 首页主题封面
        3. 首页更新内容
        '''
        result = {
            'recommand': list(),
            'theme': list(),
            'update': list(),
        }
        return self.success_response(result)
