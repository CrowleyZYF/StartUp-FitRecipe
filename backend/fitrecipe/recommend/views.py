from datetime import datetime
from base.views import BaseView
from recipe.models import Recipe
from .models import Recommend, RecommendTheme, RecommendArticle, RecommendSeries
from recipe.serializers import RecipeSerializer
from theme.serializers import ThemeSerializer
from article.serializers import ArticleSerializer, SeriesSerializer
# Create your views here.

class HomepageRecommends(BaseView):
    def get(self, request, format=None):
        h = datetime.now().hour
        r = Recommend.get_recommend_by_hour(h)
        recommend = []
        recipe_json = RecipeSerializer(r, many=True).data
        for item in recipe_json:
          item['recommendtype'] = 0
          recommend.append(item)
        for item in RecommendArticle.objects.all():
          article_json = ArticleSerializer(item.article).data
          article_json['recommendtype'] = 1
          recommend.append(article_json)
        for item in RecommendSeries.objects.all():
          series_json = SeriesSerializer(item.series).data
          series_json['recommendtype'] = 2
          recommend.append(series_json)
        result = {
            'recommend': recommend,
            'theme': [ThemeSerializer(t.theme).data for t in RecommendTheme.objects.all()],
            'update': RecipeSerializer(Recipe.get_latest_recipe(), many=True).data,
            }
        return self.success_response(result)
