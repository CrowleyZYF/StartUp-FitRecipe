# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='External',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('external_id', models.CharField(max_length=100)),
                ('external_source', models.CharField(max_length=10)),
                ('user', models.ForeignKey(related_name='externals', to='accounts.Account')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AlterUniqueTogether(
            name='external',
            unique_together=set([('external_id', 'external_source')]),
        ),
        migrations.AlterModelOptions(
            name='account',
            options={'verbose_name': 'user', 'verbose_name_plural': 'users'},
        ),
        migrations.AddField(
            model_name='account',
            name='nick_name',
            field=models.CharField(default='hello', max_length=100),
            preserve_default=False,
        ),
        migrations.AlterUniqueTogether(
            name='account',
            unique_together=set([]),
        ),
        migrations.RemoveField(
            model_name='account',
            name='external_source',
        ),
        migrations.RemoveField(
            model_name='account',
            name='external_id',
        ),
    ]
