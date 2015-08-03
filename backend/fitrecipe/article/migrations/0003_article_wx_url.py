# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0002_auto_20150611_1416'),
    ]

    operations = [
        migrations.AddField(
            model_name='article',
            name='wx_url',
            field=models.URLField(null=True, verbose_name='\u5fae\u4fe1URL', blank=True),
        ),
    ]
