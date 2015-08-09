# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0008_recipe_status'),
    ]

    operations = [
        migrations.AlterField(
            model_name='recipe',
            name='status',
            field=models.IntegerField(default=-10, help_text='\u5220\u9664\uff1a-20\uff0c\u8349\u7a3f: -10\uff0c\u6b63\u5e38\uff1a10', verbose_name='\u72b6\u6001'),
        ),
    ]
