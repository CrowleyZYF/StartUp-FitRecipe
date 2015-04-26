from rest_framework.views import APIView
from rest_framework.response import Response

from .models import Recipe
from .serializers import RecipeSerializer
# Create your views here.


class RecipeList(APIView):
    '''
    List all Recipes
    '''
    def get(self, request, format=None):
        recipes = Recipe.objects.all()
        serializer = RecipeSerializer(recipes, many=True)
        return Response(serializer.data)
