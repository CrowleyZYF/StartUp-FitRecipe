# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0007_account_is_official'),
        ('recipe', '0006_auto_20150725_1715'),
        ('article', '0005_article_read_count'),
        ('theme', '0002_theme_content'),
    ]

    operations = [
        migrations.CreateModel(
            name='RecipeCollection',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('owner', models.ForeignKey(to='accounts.Account')),
                ('recipe', models.ForeignKey(to='recipe.Recipe')),
            ],
            options={
                'verbose_name': '\u83dc\u8c31\u6536\u85cf',
                'verbose_name_plural': '\u83dc\u8c31\u6536\u85cf\u8868',
            },
        ),
        migrations.CreateModel(
            name='SeriesCollection',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('owner', models.ForeignKey(to='accounts.Account')),
                ('series', models.ForeignKey(to='article.Series')),
            ],
            options={
                'verbose_name': '\u6587\u7ae0\u7cfb\u5217\u6536\u85cf',
                'verbose_name_plural': '\u6587\u7ae0\u7cfb\u5217\u6536\u85cf\u8868',
            },
        ),
        migrations.CreateModel(
            name='ThemeCollection',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('owner', models.ForeignKey(to='accounts.Account')),
                ('theme', models.ForeignKey(to='theme.Theme')),
            ],
            options={
                'verbose_name': '\u4e3b\u9898\u6536\u85cf',
                'verbose_name_plural': '\u4e3b\u9898\u6536\u85cf\u8868',
            },
        ),
    ]
