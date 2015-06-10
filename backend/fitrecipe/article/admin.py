from django.contrib import admin
from .models import  Article, ArticleType, Series
# Register your models here.


class SeriesInline(admin.TabularInline):
    model = Series


class ArticleInline(admin.TabularInline):
    model = Article


class ArticleTypeAdmin(admin.ModelAdmin):
    list_display = ('id', 'title')
    search_fields = ('type',)
    list_display_links = ('title',)
    inlines = (SeriesInline,)


class SeriesAdmin(admin.ModelAdmin):
    list_display = ('id', 'title')
    search_fields = ('type',)
    list_display_links = ('title',)
    list_filter = ('article_type',)
    inlines = (ArticleInline,)


class ArticleAdmin(admin.ModelAdmin):
    list_display = ('id', 'title')
    search_fields = ('type',)
    list_display_links = ('title',)
    list_filter = ('series',)



admin.site.register(Series, SeriesAdmin)
admin.site.register(Article, ArticleAdmin)
admin.site.register(ArticleType, ArticleTypeAdmin)

