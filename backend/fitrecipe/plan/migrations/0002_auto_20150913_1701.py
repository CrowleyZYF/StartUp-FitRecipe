# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('plan', '0001_initial'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='dish',
            options={'verbose_name': '\u6bcf\u4e00\u987f\u7684\u83dc', 'verbose_name_plural': '\u6bcf\u4e00\u987f\u7684\u83dc\u8868'},
        ),
        migrations.AlterModelOptions(
            name='plan',
            options={'verbose_name': '\u8ba1\u5212', 'verbose_name_plural': '\u8ba1\u5212\u8868'},
        ),
        migrations.AlterModelOptions(
            name='planauthor',
            options={'verbose_name': '\u8ba1\u5212\u4f5c\u8005', 'verbose_name_plural': '\u8ba1\u5212\u4f5c\u8005\u8868'},
        ),
        migrations.AlterModelOptions(
            name='routine',
            options={'verbose_name': '\u8ba1\u5212\u4e2d\u7684\u4e00\u5929', 'verbose_name_plural': '\u8ba1\u5212\u4e2d\u7684\u4e00\u5929\u8868'},
        ),
        migrations.AlterModelOptions(
            name='singleingredient',
            options={'verbose_name': '\u8ba1\u5212\u4e2d\u7684\u98df\u6750', 'verbose_name_plural': '\u8ba1\u5212\u4e2d\u7684\u98df\u6750\u8868'},
        ),
        migrations.AlterModelOptions(
            name='singlerecipe',
            options={'verbose_name': '\u8ba1\u5212\u4e2d\u7684\u98df\u8c31', 'verbose_name_plural': '\u8ba1\u5212\u4e2d\u7684\u98df\u8c31\u8868'},
        ),
        migrations.RemoveField(
            model_name='dish',
            name='ingredient',
        ),
        migrations.RemoveField(
            model_name='dish',
            name='recipes',
        ),
        migrations.AddField(
            model_name='plan',
            name='title',
            field=models.CharField(default='12', max_length=100),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='singleingredient',
            name='dish',
            field=models.ForeignKey(default=1, to='plan.Dish'),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='singlerecipe',
            name='dish',
            field=models.ForeignKey(default=1, to='plan.Dish'),
            preserve_default=False,
        ),
    ]
