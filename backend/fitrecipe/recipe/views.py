from rest_framework.response import Response

from base.views import BaseView
from .models import Recipe
from .serializers import RecipeSerializer
# Create your views here.


class RecipeList(BaseView):
    def get(self, request, format=None):
        '''
        List all recipes
        '''
        recipes = Recipe.objects.all()
        serializer = RecipeSerializer(recipes, many=True)
        return Response(serializer.data)


class RecipeDetail(BaseView):
    def get(self, request, pk, format=None):
        '''
        return a specific recipe.
        '''
        recipe = self.get_object(Recipe, pk)
        serializer = RecipeSerializer(recipe)
        return Response(serializer.data)
