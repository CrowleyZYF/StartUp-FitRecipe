# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('collection', '0001_initial'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='recipecollection',
            unique_together=set([('recipe', 'owner')]),
        ),
        migrations.AlterUniqueTogether(
            name='seriescollection',
            unique_together=set([('series', 'owner')]),
        ),
        migrations.AlterUniqueTogether(
            name='themecollection',
            unique_together=set([('theme', 'owner')]),
        ),
    ]
