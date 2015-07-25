# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('theme', '0002_theme_content'),
        ('recommend', '0003_auto_20150725_1641'),
    ]

    operations = [
        migrations.CreateModel(
            name='RecommendTheme',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('theme', models.ForeignKey(to='theme.Theme')),
            ],
            options={
                'verbose_name': '\u9996\u9875\u63a8\u8350\u4e3b\u9898',
                'verbose_name_plural': '\u9996\u9875\u63a8\u8350\u4e3b\u9898\u8868',
            },
        ),
    ]
