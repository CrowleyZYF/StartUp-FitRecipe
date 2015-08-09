# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0007_account_is_official'),
        ('recipe', '0006_auto_20150725_1715'),
    ]

    operations = [
        migrations.CreateModel(
            name='Comment',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('content', models.CharField(max_length=200, verbose_name='\u8bc4\u8bba')),
                ('author', models.ForeignKey(verbose_name='\u4f5c\u8005', to='accounts.Account')),
                ('recipe', models.ForeignKey(verbose_name='\u83dc\u8c31', to='recipe.Recipe')),
                ('reply', models.ForeignKey(related_name='reply_set', verbose_name='\u56de\u590d', blank=True, to='accounts.Account', null=True)),
            ],
            options={
                'verbose_name': '\u8bc4\u8bba',
                'verbose_name_plural': '\u8bc4\u8bba\u8868',
            },
        ),
    ]
