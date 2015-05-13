from django.contrib import admin

# Register your models here.
from .models import Account, External

admin.site.register(Account)
admin.site.register(External)
