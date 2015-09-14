# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0002_auto_20150913_1701'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='dish',
            options={},
        ),
        migrations.AlterModelOptions(
            name='plan',
            options={},
        ),
        migrations.AlterModelOptions(
            name='planauthor',
            options={},
        ),
        migrations.AlterModelOptions(
            name='routine',
            options={},
        ),
        migrations.AlterModelOptions(
            name='singleingredient',
            options={},
        ),
        migrations.AlterModelOptions(
            name='singlerecipe',
            options={},
        ),
        migrations.AddField(
            model_name='routine',
            name='title',
            field=models.CharField(default='aa', help_text='\u8d77\u4e00\u4e2a\u4f60\u81ea\u5df1\u770b\u5f97\u61c2\u7684\u540d\u5b57\uff0c\u641c\u7d22\u7b5b\u9009\u7528', max_length=200),
            preserve_default=False,
        ),
    ]
