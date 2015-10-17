# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0012_punch'),
    ]

    operations = [
        migrations.AddField(
            model_name='punch',
            name='state',
            field=models.IntegerField(default=10),
        ),
    ]
