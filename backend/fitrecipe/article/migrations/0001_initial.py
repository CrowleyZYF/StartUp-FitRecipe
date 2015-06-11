# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Article',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('title', models.CharField(max_length=50, verbose_name='\u6807\u9898')),
                ('img_cover', models.URLField(verbose_name='\u5c01\u9762\u56fe\u7247')),
                ('content', models.TextField(verbose_name='\u6b63\u6587')),
            ],
            options={
                'verbose_name': '\u6587\u7ae0',
                'verbose_name_plural': '\u6587\u7ae0\u8868',
            },
        ),
        migrations.CreateModel(
            name='ArticleType',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('title', models.CharField(max_length=50, verbose_name='\u6807\u9898')),
                ('introduce', models.TextField(verbose_name='\u63cf\u8ff0')),
            ],
            options={
                'verbose_name': '\u6587\u7ae0\u7c7b\u522b',
                'verbose_name_plural': '\u6587\u7ae0\u7c7b\u522b\u8868',
            },
        ),
        migrations.CreateModel(
            name='Series',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('title', models.CharField(max_length=50, verbose_name='\u6807\u9898')),
                ('author', models.CharField(max_length=50, verbose_name='\u4f5c\u8005\u540d\u5b57')),
                ('author_avatar', models.URLField(verbose_name='\u4f5c\u8005\u5934\u50cf')),
                ('author_type', models.CharField(max_length=50, verbose_name='\u4f5c\u8005\u7c7b\u578b')),
                ('article_type', models.ForeignKey(to='article.ArticleType')),
            ],
            options={
                'verbose_name': '\u7cfb\u5217',
                'verbose_name_plural': '\u7cfb\u5217\u8868',
            },
        ),
        migrations.AddField(
            model_name='article',
            name='series',
            field=models.ForeignKey(to='article.Series'),
        ),
    ]
