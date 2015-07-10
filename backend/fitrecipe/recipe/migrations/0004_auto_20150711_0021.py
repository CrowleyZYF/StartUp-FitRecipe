# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0003_auto_20150710_2250'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='procedure',
            unique_together=set([]),
        ),
    ]
