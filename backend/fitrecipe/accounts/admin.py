from django.contrib import admin

# Register your models here.
from .models import Account, External, OtherAuthor, Evaluation

admin.site.register(Account)
admin.site.register(External)
admin.site.register(OtherAuthor)
admin.site.register(Evaluation)