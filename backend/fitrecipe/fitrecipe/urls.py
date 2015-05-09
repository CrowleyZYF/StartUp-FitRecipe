from django.conf.urls import patterns, include, url
from django.contrib import admin

from recipe import views as recipe_views
from accounts import views as accounts_views

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'fitrecipe.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    # If you're intending to use the browsable API you'll probably also want to add REST framework's login and logout views.
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    # Recipe
    url(r'^api/recipes/$', recipe_views.RecipeList.as_view()),
    url(r'^api/recipe/(\d+)/$', recipe_views.RecipeDetail.as_view()),
    url(r'^api/accounts/login/$', accounts_views.LoginView.as_view()),
    url(r'^api/accounts/register/$', accounts_views.RegisterView.as_view()),
    url(r'^api/accounts/thirdparty/$', accounts_views.ThirdPartyLogin.as_view()),
)
