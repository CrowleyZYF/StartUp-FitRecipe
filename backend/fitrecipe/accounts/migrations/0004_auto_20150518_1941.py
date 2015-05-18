# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0003_account_avater'),
    ]

    operations = [
        migrations.RenameField(
            model_name='account',
            old_name='avater',
            new_name='avatar',
        ),
    ]
