from django.conf.urls import patterns, include, url
from django.contrib import admin
from ajax_select import urls as ajax_select_urls

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'fitrecipe.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    # nested admin
    url(r'^nested_admin/', include('nested_admin.urls')),
    # django admin
    url(r'^admin/', include(admin.site.urls)),
    # ajax_select_urls
    url(r'^admin/lookups/', include(ajax_select_urls)),
    # Recipe
    url(r'^api/recipe/', include('recipe.urls')),
    # Accounts
    url(r'^api/accounts/', include('accounts.urls')),
    # Label
    url(r'^api/label/', include('label.urls')),
    # Theme
    url(r'^api/theme/', include('theme.urls')),
    # Recommend
    url(r'^api/recommend/', include('recommend.urls')),
    # article
    url(r'^api/article/', include('article.urls')),
    # comment
    url(r'^api/comment/', include('comment.urls')),
    # collection
    url(r'^api/collection/', include('collection.urls')),
    # plan
    url(r'^api/plan/', include('plan.urls')),
)
