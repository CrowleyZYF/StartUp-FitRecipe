from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'fitrecipe.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    # If you're intending to use the browsable API you'll probably also want to add REST framework's login and logout views.
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    # Recipe
    url(r'^api/recipe/', include('recipe.urls')),
    # Accounts
    url(r'^api/accounts/', include('accounts.urls')),
    # Label
    url(r'^api/label/', include('label.urls')),
)
