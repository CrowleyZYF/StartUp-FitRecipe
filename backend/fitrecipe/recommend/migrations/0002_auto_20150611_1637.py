# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
        ('recommend', '0001_initial'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='recommend',
            options={'verbose_name': '\u63a8\u8350', 'verbose_name_plural': '\u63a8\u8350\u8868'},
        ),
        migrations.AlterField(
            model_name='recommend',
            name='end_time',
            field=models.IntegerField(help_text='\u7ed3\u675f\u63a8\u8350\u65f6\u95f4\uff0c0~23', verbose_name='\u7ed3\u675f\u65f6\u95f4', validators=[django.core.validators.MaxValueValidator(23), django.core.validators.MinValueValidator(0)]),
        ),
        migrations.AlterField(
            model_name='recommend',
            name='start_time',
            field=models.IntegerField(help_text='\u5f00\u59cb\u63a8\u8350\u65f6\u95f4\uff0c0~23', verbose_name='\u5f00\u59cb\u65f6\u95f4', validators=[django.core.validators.MaxValueValidator(23), django.core.validators.MinValueValidator(0)]),
        ),
    ]
