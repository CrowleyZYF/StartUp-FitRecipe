# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('auth', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Account',
            fields=[
                ('user_ptr', models.OneToOneField(parent_link=True, auto_created=True, primary_key=True, serialize=False, to=settings.AUTH_USER_MODEL)),
                ('phone', models.CharField(max_length=30, unique=True, null=True)),
                ('is_changed_nick', models.BooleanField(default=False)),
                ('external_id', models.CharField(max_length=100, null=True)),
                ('external_source', models.CharField(max_length=10, null=True)),
            ],
            options={
            },
            bases=('auth.user',),
        ),
        migrations.AlterUniqueTogether(
            name='account',
            unique_together=set([('external_id', 'external_source')]),
        ),
    ]
