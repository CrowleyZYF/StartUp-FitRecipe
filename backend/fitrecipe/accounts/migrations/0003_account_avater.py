# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0002_auto_20150509_1327'),
    ]

    operations = [
        migrations.AddField(
            model_name='account',
            name='avater',
            field=models.URLField(default=b'http://tp2.sinaimg.cn/1937464505/180/5708528601/1'),
            preserve_default=True,
        ),
    ]
