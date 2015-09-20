# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import datetime


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0008_auto_20150920_2044'),
    ]

    operations = [
        migrations.AddField(
            model_name='plan',
            name='authored_date',
            field=models.DateField(default=datetime.date(2015, 9, 20), auto_now_add=True),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='plan',
            name='is_personal',
            field=models.BooleanField(default=True),
        ),
        migrations.AlterField(
            model_name='plan',
            name='title',
            field=models.CharField(default=b'personal plan', max_length=100),
        ),
        migrations.AlterField(
            model_name='routine',
            name='day',
            field=models.IntegerField(default=1, help_text='Plan \u4e2d\u7684\u7b2c\u51e0\u5929'),
        ),
    ]
