# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0008_evaluation'),
    ]

    operations = [
        migrations.AlterField(
            model_name='evaluation',
            name='user',
            field=models.ForeignKey(verbose_name='\u7528\u6237', to='accounts.Account'),
        ),
    ]
