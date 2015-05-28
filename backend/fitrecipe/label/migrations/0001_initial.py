# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Label',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25, verbose_name='\u6807\u7b7e\u540d\u79f0')),
                ('type', models.CharField(max_length=25, verbose_name='\u6807\u7b7e\u7c7b\u578b', choices=[('\u529f\u6548', '\u529f\u6548'), ('\u7528\u9910\u65f6\u95f4', '\u7528\u9910\u65f6\u95f4'), ('\u98df\u6750', '\u98df\u6750'), ('\u5176\u4ed6', '\u5176\u4ed6')])),
            ],
            options={
                'verbose_name': '\u6807\u7b7e',
                'verbose_name_plural': '\u6807\u7b7e\u8868',
            },
        ),
    ]
