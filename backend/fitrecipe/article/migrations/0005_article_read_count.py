# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0004_auto_20150805_2245'),
    ]

    operations = [
        migrations.AddField(
            model_name='article',
            name='read_count',
            field=models.IntegerField(default=0, verbose_name='\u9605\u8bfb\u6570'),
        ),
    ]
