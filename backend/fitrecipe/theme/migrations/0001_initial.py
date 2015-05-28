# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Theme',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('title', models.CharField(max_length=25, verbose_name='\u4e3b\u9898\u540d\u5b57')),
                ('img', models.URLField(verbose_name='\u4e3b\u9898\u56fe\u7247')),
                ('thumbnail', models.URLField(verbose_name='\u7f29\u7565\u56fe\u56fe\u7247')),
                ('recipes', models.ManyToManyField(to='recipe.Recipe', verbose_name='\u5305\u542b\u7684\u83dc\u8c31')),
            ],
            options={
                'verbose_name': '\u4e3b\u9898',
                'verbose_name_plural': '\u4e3b\u9898\u8868',
            },
        ),
    ]
