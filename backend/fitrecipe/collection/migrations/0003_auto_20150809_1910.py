# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('collection', '0002_auto_20150808_2051'),
    ]

    operations = [
        migrations.AddField(
            model_name='recipecollection',
            name='status',
            field=models.IntegerField(default=10, verbose_name='\u72b6\u6001'),
        ),
        migrations.AddField(
            model_name='seriescollection',
            name='status',
            field=models.IntegerField(default=10, verbose_name='\u72b6\u6001'),
        ),
        migrations.AddField(
            model_name='themecollection',
            name='status',
            field=models.IntegerField(default=10, verbose_name='\u72b6\u6001'),
        ),
    ]
