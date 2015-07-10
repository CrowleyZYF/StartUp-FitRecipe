# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0005_auto_20150522_2318'),
    ]

    operations = [
        migrations.CreateModel(
            name='OtherAuthor',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('avatar', models.URLField(default=b'http://tp2.sinaimg.cn/1937464505/180/5708528601/1')),
                ('nick_name', models.CharField(max_length=100)),
                ('sex', models.CharField(max_length=10)),
            ],
            options={
                'verbose_name': '\u975e\u6ce8\u518c\u7528\u6237',
                'verbose_name_plural': '\u975e\u6ce8\u518c\u7528\u6237\u8868',
            },
        ),
    ]
