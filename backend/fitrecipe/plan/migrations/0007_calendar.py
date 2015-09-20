# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0008_evaluation'),
        ('plan', '0006_auto_20150920_1757'),
    ]

    operations = [
        migrations.CreateModel(
            name='Calendar',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('joined_date', models.DateField(auto_now_add=True)),
                ('plan', models.ForeignKey(to='plan.Plan')),
                ('user', models.ForeignKey(to='accounts.Account')),
            ],
            options={
                'abstract': False,
            },
        ),
    ]
