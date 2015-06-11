# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Recommend',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25, verbose_name='\u540d\u5b57')),
                ('start_time', models.IntegerField(verbose_name='\u5f00\u59cb\u65f6\u95f4', validators=[django.core.validators.MaxValueValidator(23), django.core.validators.MinLengthValidator(0)])),
                ('end_time', models.IntegerField(verbose_name='\u7ed3\u675f\u65f6\u95f4', validators=[django.core.validators.MaxValueValidator(23), django.core.validators.MinLengthValidator(0)])),
                ('recipes', models.ManyToManyField(to='recipe.Recipe', verbose_name='\u63a8\u8350\u83dc\u8c31')),
            ],
            options={
                'verbose_name': '\u6807\u7b7e',
                'verbose_name_plural': '\u6807\u7b7e\u8868',
            },
        ),
    ]
