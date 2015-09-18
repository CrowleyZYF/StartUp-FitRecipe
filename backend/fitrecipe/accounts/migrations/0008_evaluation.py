# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0007_account_is_official'),
    ]

    operations = [
        migrations.CreateModel(
            name='Evaluation',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('gender', models.IntegerField(verbose_name='\u6027\u522b', validators=[django.core.validators.MinValueValidator(0), django.core.validators.MaxValueValidator(1)])),
                ('age', models.IntegerField(verbose_name='\u5e74\u9f84', validators=django.core.validators.MinValueValidator(5))),
                ('height', models.PositiveIntegerField(verbose_name='\u8eab\u9ad8')),
                ('weight', models.FloatField(verbose_name='\u4f53\u91cd', validators=django.core.validators.MinValueValidator(0.0))),
                ('roughFat', models.IntegerField(verbose_name='\u7c97\u7565\u4f53\u8102', validators=[django.core.validators.MinValueValidator(0), django.core.validators.MaxValueValidator(4)])),
                ('preciseFat', models.FloatField(verbose_name='\u7cbe\u786e\u4f53\u8102', validators=django.core.validators.MinValueValidator(0.0))),
                ('jobType', models.IntegerField(verbose_name='\u5de5\u4f5c\u5f3a\u5ea6', validators=[django.core.validators.MinValueValidator(0), django.core.validators.MaxValueValidator(3)])),
                ('goalType', models.IntegerField(verbose_name='\u76ee\u6807', validators=[django.core.validators.MinValueValidator(0), django.core.validators.MaxValueValidator(1)])),
                ('exerciseFrequency', models.IntegerField(verbose_name='\u6bcf\u5468\u8fd0\u52a8\u6b21\u6570')),
                ('exerciseInterval', models.IntegerField(verbose_name='\u8fd0\u52a8\u65f6\u957f', validators=[django.core.validators.MinValueValidator(0), django.core.validators.MaxValueValidator(3)])),
                ('weightGoal', models.FloatField(verbose_name='\u76ee\u6807\u4f53\u91cd', validators=django.core.validators.MinValueValidator(0.0))),
                ('daysToGoal', models.PositiveIntegerField(verbose_name='\u65f6\u95f4')),
                ('user', models.ForeignKey(to='accounts.Account')),
            ],
            options={
                'verbose_name': '\u8bc4\u6d4b\u62a5\u544a',
                'verbose_name_plural': '\u8bc4\u6d4b\u62a5\u544a\u8868',
            },
        ),
    ]
