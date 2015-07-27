# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recommend', '0004_recommendtheme'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='recommend',
            options={'verbose_name': '\u63a8\u8350\u83dc\u8c31', 'verbose_name_plural': '\u63a8\u8350\u83dc\u8c31\u8868'},
        ),
        migrations.RemoveField(
            model_name='recommend',
            name='img',
        ),
        migrations.RemoveField(
            model_name='recommend',
            name='thumbnail',
        ),
    ]
