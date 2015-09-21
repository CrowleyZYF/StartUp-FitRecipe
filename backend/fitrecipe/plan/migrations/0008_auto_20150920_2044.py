# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0007_calendar'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='calendar',
            unique_together=set([('user', 'joined_date')]),
        ),
    ]
