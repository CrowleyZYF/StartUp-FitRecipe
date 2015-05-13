# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0004_auto_20150513_2120'),
    ]

    operations = [
        migrations.AlterField(
            model_name='recipe',
            name='effect_labels',
            field=models.ManyToManyField(related_name='effect_set', null=True, to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='meat_labels',
            field=models.ManyToManyField(related_name='meat_set', null=True, to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='other_labels',
            field=models.ManyToManyField(related_name='other_set', null=True, to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='time_labels',
            field=models.ManyToManyField(related_name='time_set', null=True, to='recipe.Label', blank=True),
            preserve_default=True,
        ),
    ]
