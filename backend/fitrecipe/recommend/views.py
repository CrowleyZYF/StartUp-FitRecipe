from datetime import datetime
from base.views import BaseView
from recipe.models import Recipe
from .models import Recommend, RecommendTheme
from recipe.serializers import RecipeSerializer
from theme.serializers import ThemeSerializer
# Create your views here.

class HomepageRecommends(BaseView):
    def get(self, request, format=None):
        h = datetime.now().hour
        r = Recommend.get_recommend_by_hour(h)
        result = {
            'recommend': RecipeSerializer(r, many=True).data,
            'theme': [ThemeSerializer(t.theme).data for t in RecommendTheme.objects.all()],
            'update': RecipeSerializer(Recipe.get_latest_recipe(), many=True).data,
            }
        return self.success_response(result)