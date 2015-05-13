# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0002_auto_20150512_2131'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='recipe',
            name='labels',
        ),
        migrations.AddField(
            model_name='recipe',
            name='effect_labels',
            field=models.ManyToManyField(related_name='effect_set', to='recipe.Label'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='recipe',
            name='meat_labels',
            field=models.ManyToManyField(related_name='meat_set', to='recipe.Label'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='recipe',
            name='other_labels',
            field=models.ManyToManyField(related_name='other_set', to='recipe.Label'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='recipe',
            name='time_labels',
            field=models.ManyToManyField(related_name='time_set', to='recipe.Label'),
            preserve_default=True,
        ),
    ]
