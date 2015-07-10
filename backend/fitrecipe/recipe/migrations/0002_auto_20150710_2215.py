# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='recipe',
            name='introduce',
            field=models.TextField(null=True, verbose_name='\u83dc\u8c31\u7b80\u4ecb', blank=True),
        ),
        migrations.AddField(
            model_name='recipe',
            name='tips',
            field=models.TextField(null=True, verbose_name='\u83dc\u8c31\u5c0f\u8d34\u58eb', blank=True),
        ),
    ]
