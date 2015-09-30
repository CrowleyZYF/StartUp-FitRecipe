# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0009_auto_20150927_2154'),
        ('plan', '0011_auto_20150927_2254'),
    ]

    operations = [
        migrations.CreateModel(
            name='Punch',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('type', models.IntegerField()),
                ('img', models.URLField()),
                ('date', models.DateField(auto_now_add=True)),
                ('user', models.ForeignKey(to='accounts.Account')),
            ],
            options={
                'abstract': False,
            },
        ),
    ]
