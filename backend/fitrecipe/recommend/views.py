from datetime import datetime
from base.views import BaseView
from .models import Recommend
from recipe.serializers import RecipeSerializer
# Create your views here.

class RecommendRecipes(BaseView):
    def get(self, request, format=None):
        h = datetime.now().hour
        recipes = Recommend.get_recommend_recipes(h)
        return self.success_response(RecipeSerializer(recipes, many=True).data)