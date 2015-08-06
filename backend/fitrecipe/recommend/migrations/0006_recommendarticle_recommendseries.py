# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0004_auto_20150805_2245'),
        ('recommend', '0005_auto_20150725_1715'),
    ]

    operations = [
        migrations.CreateModel(
            name='RecommendArticle',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('article', models.ForeignKey(to='article.Article')),
            ],
            options={
                'verbose_name': '\u9996\u9875\u63a8\u8350\u6587\u7ae0',
                'verbose_name_plural': '\u9996\u9875\u63a8\u8350\u6587\u7ae0\u8868',
            },
        ),
        migrations.CreateModel(
            name='RecommendSeries',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('series', models.ForeignKey(to='article.Series')),
            ],
            options={
                'verbose_name': '\u9996\u9875\u63a8\u8350\u7cfb\u5217',
                'verbose_name_plural': '\u9996\u9875\u63a8\u8350\u7cfb\u5217\u8868',
            },
        ),
    ]
