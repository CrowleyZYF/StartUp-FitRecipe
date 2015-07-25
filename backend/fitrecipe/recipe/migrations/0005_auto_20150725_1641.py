# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0004_auto_20150711_0021'),
    ]

    operations = [
        migrations.AlterField(
            model_name='recipe',
            name='calories',
            field=models.FloatField(default=0, help_text='\u81ea\u52a8\u8ba1\u7b97\uff0c\u4e0d\u7528\u586b', verbose_name='\u6bcf\u767e\u514b\u5361\u8def\u91cc'),
        ),
    ]
