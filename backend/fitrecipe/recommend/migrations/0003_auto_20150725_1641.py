# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recommend', '0002_auto_20150611_1637'),
    ]

    operations = [
        migrations.AddField(
            model_name='recommend',
            name='img',
            field=models.URLField(null=True, verbose_name='\u5927\u56fe URL', blank=True),
        ),
        migrations.AddField(
            model_name='recommend',
            name='thumbnail',
            field=models.URLField(null=True, verbose_name='\u7f29\u7565\u56fe URL', blank=True),
        ),
    ]
