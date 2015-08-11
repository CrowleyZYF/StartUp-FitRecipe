# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0007_recipe_collection_count'),
    ]

    operations = [
        migrations.AddField(
            model_name='recipe',
            name='status',
            field=models.IntegerField(default=-10, verbose_name='\u72b6\u6001'),
        ),
    ]
