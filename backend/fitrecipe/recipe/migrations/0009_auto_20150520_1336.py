# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0008_auto_20150518_1938'),
    ]

    operations = [
        migrations.AddField(
            model_name='recipe',
            name='calories',
            field=models.FloatField(default=0, help_text='\u81ea\u52a8\u8ba1\u7b97\uff0c\u4e0d\u7528\u586b', verbose_name='\u5361\u8def\u91cc'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='duration',
            field=models.IntegerField(help_text='\u5206\u949f', verbose_name='\u70f9\u996a\u65f6\u95f4'),
            preserve_default=True,
        ),
    ]
