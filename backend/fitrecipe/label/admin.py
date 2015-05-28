from django.contrib import admin

from .models import Label


# Register your models here.
class LabelAdmin(admin.ModelAdmin):
    # http://stackoverflow.com/questions/16235201/list-display-how-to-display-value-from-choices
    list_display = ('id', 'name', 'get_type_display')
    list_filter = ('type',)
    search_fields = ('name',)
    list_display_links = ('name',)

admin.site.register(Label, LabelAdmin)
