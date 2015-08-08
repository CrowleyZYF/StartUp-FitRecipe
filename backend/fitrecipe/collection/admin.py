from django.contrib import admin

# Register your models here.
from .models import RecipeCollection, ThemeCollection, SeriesCollection

admin.site.register(RecipeCollection)
admin.site.register(ThemeCollection)
admin.site.register(SeriesCollection)
