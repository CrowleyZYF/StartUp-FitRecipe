# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0008_evaluation'),
        ('plan', '0003_auto_20150913_1941'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='personalplan',
            name='routine',
        ),
        migrations.RemoveField(
            model_name='personalplan',
            name='user',
        ),
        migrations.RemoveField(
            model_name='plan',
            name='routine',
        ),
        migrations.RemoveField(
            model_name='routine',
            name='title',
        ),
        migrations.AddField(
            model_name='plan',
            name='is_personal',
            field=models.BooleanField(default=False),
        ),
        migrations.AddField(
            model_name='plan',
            name='user',
            field=models.ForeignKey(blank=True, to='accounts.Account', null=True),
        ),
        migrations.AddField(
            model_name='routine',
            name='plan',
            field=models.ForeignKey(default='123', to='plan.Plan'),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='dish',
            name='type',
            field=models.IntegerField(help_text='0-\u65e9\u9910 1-\u4e0a\u5348\u52a0\u9910 2-\u5348\u9910 3-\u4e0b\u5348\u52a0\u9910 5-\u665a\u9910'),
        ),
        migrations.AlterField(
            model_name='plan',
            name='author',
            field=models.ForeignKey(blank=True, to='plan.PlanAuthor', null=True),
        ),
        migrations.AlterField(
            model_name='plan',
            name='benifit',
            field=models.IntegerField(default=0),
        ),
        migrations.AlterField(
            model_name='plan',
            name='dish_headcount',
            field=models.IntegerField(default=1),
        ),
        migrations.AlterField(
            model_name='plan',
            name='img',
            field=models.URLField(null=True, blank=True),
        ),
        migrations.AlterField(
            model_name='plan',
            name='inrtoduce',
            field=models.TextField(default=b''),
        ),
        migrations.AlterField(
            model_name='plan',
            name='total_days',
            field=models.IntegerField(default=1),
        ),
        migrations.AlterField(
            model_name='routine',
            name='day',
            field=models.IntegerField(help_text='Plan \u4e2d\u7684\u7b2c\u51e0\u5929'),
        ),
        migrations.DeleteModel(
            name='PersonalPlan',
        ),
    ]
