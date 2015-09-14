# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('recipe', '0009_auto_20150809_1925'),
        ('accounts', '0007_account_is_official'),
    ]

    operations = [
        migrations.CreateModel(
            name='Dish',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('type', models.IntegerField()),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='PersonalPlan',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Plan',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('img', models.URLField()),
                ('inrtoduce', models.TextField()),
                ('difficulty', models.IntegerField(default=1)),
                ('delicious', models.IntegerField(default=3)),
                ('benifit', models.IntegerField()),
                ('total_days', models.IntegerField()),
                ('dish_headcount', models.IntegerField()),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='PlanAuthor',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=100)),
                ('type', models.IntegerField()),
                ('avatar', models.URLField()),
                ('job_title', models.CharField(max_length=100, null=True, blank=True)),
                ('fit_duration', models.CharField(max_length=100, null=True, blank=True)),
                ('fat', models.CharField(max_length=100, null=True, blank=True)),
                ('introduce', models.TextField(null=True, blank=True)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Routine',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('day', models.IntegerField()),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='SingleIngredient',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('amount', models.IntegerField()),
                ('ingredient', models.ForeignKey(to='recipe.Ingredient')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='SingleRecipe',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('amount', models.IntegerField()),
                ('recipe', models.ForeignKey(to='recipe.Recipe')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.AddField(
            model_name='plan',
            name='author',
            field=models.ForeignKey(to='plan.PlanAuthor'),
        ),
        migrations.AddField(
            model_name='plan',
            name='routine',
            field=models.ManyToManyField(to='plan.Routine'),
        ),
        migrations.AddField(
            model_name='personalplan',
            name='routine',
            field=models.ManyToManyField(to='plan.Routine'),
        ),
        migrations.AddField(
            model_name='personalplan',
            name='user',
            field=models.ForeignKey(to='accounts.Account'),
        ),
        migrations.AddField(
            model_name='dish',
            name='ingredient',
            field=models.ManyToManyField(to='plan.SingleIngredient'),
        ),
        migrations.AddField(
            model_name='dish',
            name='recipes',
            field=models.ManyToManyField(to='plan.SingleRecipe'),
        ),
        migrations.AddField(
            model_name='dish',
            name='routine',
            field=models.ForeignKey(to='plan.Routine'),
        ),
    ]
