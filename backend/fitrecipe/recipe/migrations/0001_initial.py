# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Recipe',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('img_height', models.PositiveIntegerField(null=True, blank=True)),
                ('img_width', models.PositiveIntegerField(null=True, blank=True)),
                ('img', models.ImageField(height_field=b'img_height', width_field=b'img_width', upload_to=b'static/images')),
                ('thumbnail', models.ImageField(null=True, upload_to=b'static/images', blank=True)),
                ('title', models.CharField(max_length=100)),
                ('type', models.IntegerField(default=0, max_length=5)),
                ('duration', models.CharField(max_length=20, null=True, blank=True)),
                ('calorie', models.CharField(max_length=100, null=True, blank=True)),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
    ]
