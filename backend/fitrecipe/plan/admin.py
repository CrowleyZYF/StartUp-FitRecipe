from django.contrib import admin

# Register your models here.
from .models import *

class SingleIngredientInline(admin.TabularInline):
    model = SingleIngredient


class SingleRecipeInline(admin.TabularInline):
    model = SingleRecipe


class DishAdmin(admin.ModelAdmin):
    inlines = (SingleIngredientInline, SingleRecipeInline)


class RoutineAdmin(admin.ModelAdmin):
    readonly_fields = ('dish', )


class PlanAdmin(admin.ModelAdmin):
    filter_horizontal = ('routine',)


admin.site.register(PlanAuthor)
admin.site.register(Plan, PlanAdmin)
admin.site.register(Dish, DishAdmin)
admin.site.register(Routine, RoutineAdmin)
