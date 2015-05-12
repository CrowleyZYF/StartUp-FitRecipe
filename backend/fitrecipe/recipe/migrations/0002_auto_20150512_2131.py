# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Component',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('amount', models.IntegerField()),
                ('remark', models.CharField(max_length=100, null=True, blank=True)),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Ingredient',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25)),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Label',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25)),
                ('type', models.CharField(max_length=25)),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Nutrition',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25)),
                ('eng_name', models.CharField(max_length=100)),
                ('amount', models.FloatField()),
                ('unit', models.CharField(max_length=25)),
                ('ingredient', models.ForeignKey(to='recipe.Ingredient')),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Procedure',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('num', models.IntegerField()),
                ('content', models.TextField()),
                ('img', models.URLField(null=True, blank=True)),
                ('recipe', models.ForeignKey(to='recipe.Recipe')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AlterUniqueTogether(
            name='procedure',
            unique_together=set([('recipe', 'num')]),
        ),
        migrations.AddField(
            model_name='component',
            name='ingredient',
            field=models.ForeignKey(to='recipe.Ingredient'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='component',
            name='recipe',
            field=models.ForeignKey(to='recipe.Recipe'),
            preserve_default=True,
        ),
        migrations.RemoveField(
            model_name='recipe',
            name='calorie',
        ),
        migrations.RemoveField(
            model_name='recipe',
            name='img_height',
        ),
        migrations.RemoveField(
            model_name='recipe',
            name='img_width',
        ),
        migrations.RemoveField(
            model_name='recipe',
            name='type',
        ),
        migrations.AddField(
            model_name='recipe',
            name='labels',
            field=models.ManyToManyField(to='recipe.Label'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='duration',
            field=models.IntegerField(default=10),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='img',
            field=models.URLField(),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='thumbnail',
            field=models.URLField(default='http://163.com'),
            preserve_default=False,
        ),
    ]
