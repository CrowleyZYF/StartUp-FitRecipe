# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('theme', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='theme',
            name='content',
            field=models.TextField(null=True, verbose_name='\u4e3b\u9898\u63cf\u8ff0', blank=True),
        ),
    ]
