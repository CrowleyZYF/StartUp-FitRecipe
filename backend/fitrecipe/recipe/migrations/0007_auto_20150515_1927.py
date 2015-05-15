# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0006_auto_20150514_2150'),
    ]

    operations = [
        migrations.AlterField(
            model_name='label',
            name='type',
            field=models.CharField(max_length=25, choices=[(b'\xe5\x8a\x9f\xe6\x95\x88', b'\xe5\x8a\x9f\xe6\x95\x88'), (b'\xe7\x94\xa8\xe9\xa4\x90\xe6\x97\xb6\xe9\x97\xb4', b'\xe7\x94\xa8\xe9\xa4\x90\xe6\x97\xb6\xe9\x97\xb4'), (b'\xe9\xa3\x9f\xe6\x9d\x90', b'\xe9\xa3\x9f\xe6\x9d\x90'), (b'\xe5\x85\xb6\xe4\xbb\x96', b'\xe5\x85\xb6\xe4\xbb\x96')]),
            preserve_default=True,
        ),
    ]
