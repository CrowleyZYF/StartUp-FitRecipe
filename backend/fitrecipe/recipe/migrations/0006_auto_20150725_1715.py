# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0005_auto_20150725_1641'),
    ]

    operations = [
        migrations.AddField(
            model_name='recipe',
            name='recommend_img',
            field=models.URLField(null=True, verbose_name='\u63a8\u8350\u5927\u56fe URL', blank=True),
        ),
        migrations.AddField(
            model_name='recipe',
            name='recommend_thumbnail',
            field=models.URLField(null=True, verbose_name='\u63a8\u8350\u7f29\u7565\u56fe URL', blank=True),
        ),
    ]
