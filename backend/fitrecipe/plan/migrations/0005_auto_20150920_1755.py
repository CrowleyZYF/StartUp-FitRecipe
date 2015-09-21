# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0004_auto_20150920_1712'),
    ]

    operations = [
        migrations.AlterField(
            model_name='routine',
            name='plan',
            field=models.ForeignKey(related_name='routines', to='plan.Plan'),
        ),
    ]
