# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0009_auto_20150920_2135'),
    ]

    operations = [
        migrations.AddField(
            model_name='plan',
            name='cover',
            field=models.URLField(null=True, blank=True),
        ),
        migrations.AlterField(
            model_name='calendar',
            name='joined_date',
            field=models.DateField(),
        ),
    ]
