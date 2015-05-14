# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0005_auto_20150513_2124'),
    ]

    operations = [
        migrations.AddField(
            model_name='ingredient',
            name='eng_name',
            field=models.CharField(max_length=100, null=True, blank=True),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='ingredient',
            name='ndbno',
            field=models.CharField(default='16123', max_length=25),
            preserve_default=False,
        ),
    ]
