# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0010_auto_20150927_2154'),
    ]

    operations = [
        migrations.AddField(
            model_name='plan',
            name='brief',
            field=models.TextField(default=b''),
        ),
        migrations.AddField(
            model_name='plan',
            name='internal_label',
            field=models.TextField(default=0, help_text='0-\u672a\u6307\u5b9a\uff0c1-\u98df\u8c31\uff0c2-\u98df\u6750'),
        ),
    ]
