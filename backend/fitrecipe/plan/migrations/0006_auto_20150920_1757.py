# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0005_auto_20150920_1755'),
    ]

    operations = [
        migrations.AlterField(
            model_name='routine',
            name='plan',
            field=models.ForeignKey(to='plan.Plan'),
        ),
    ]
