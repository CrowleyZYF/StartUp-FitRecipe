# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0005_auto_20150522_2318'),
        ('label', '0001_initial'),
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
                'verbose_name': '\u6784\u6210',
                'verbose_name_plural': '\u6784\u6210\u8868',
            },
        ),
        migrations.CreateModel(
            name='Ingredient',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('name', models.CharField(max_length=25)),
                ('eng_name', models.CharField(max_length=100, null=True, blank=True)),
                ('ndbno', models.CharField(max_length=25)),
            ],
            options={
                'verbose_name': '\u539f\u6599',
                'verbose_name_plural': '\u539f\u6599\u8868',
            },
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
                'verbose_name': '\u8425\u517b',
                'verbose_name_plural': '\u8425\u517b\u8868',
            },
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
            ],
            options={
                'verbose_name': '\u6b65\u9aa4',
                'verbose_name_plural': '\u6b65\u9aa4\u8868',
            },
        ),
        migrations.CreateModel(
            name='Recipe',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('img', models.URLField(verbose_name='\u5927\u56fe URL')),
                ('thumbnail', models.URLField(verbose_name='\u7f29\u7565\u56fe URL')),
                ('title', models.CharField(max_length=100, verbose_name='\u83dc\u8c31\u540d\u79f0')),
                ('duration', models.IntegerField(help_text='\u5206\u949f', verbose_name='\u70f9\u996a\u65f6\u95f4')),
                ('calories', models.FloatField(default=0, help_text='\u81ea\u52a8\u8ba1\u7b97\uff0c\u4e0d\u7528\u586b', verbose_name='\u5361\u8def\u91cc')),
                ('author', models.ForeignKey(to='accounts.Account')),
                ('effect_labels', models.ManyToManyField(related_name='effect_set', verbose_name='\u529f\u6548\u6807\u7b7e', to='label.Label')),
                ('meat_labels', models.ManyToManyField(related_name='meat_set', verbose_name='\u98df\u6750\u6807\u7b7e', to='label.Label')),
                ('other_labels', models.ManyToManyField(related_name='other_set', verbose_name='\u5176\u4ed6\u6807\u7b7e', to='label.Label')),
                ('time_labels', models.ManyToManyField(related_name='time_set', verbose_name='\u7528\u9910\u65f6\u95f4\u6807\u7b7e', to='label.Label')),
            ],
            options={
                'verbose_name': '\u83dc\u8c31',
                'verbose_name_plural': '\u83dc\u8c31\u8868',
            },
        ),
        migrations.AddField(
            model_name='procedure',
            name='recipe',
            field=models.ForeignKey(to='recipe.Recipe'),
        ),
        migrations.AddField(
            model_name='component',
            name='ingredient',
            field=models.ForeignKey(to='recipe.Ingredient'),
        ),
        migrations.AddField(
            model_name='component',
            name='recipe',
            field=models.ForeignKey(to='recipe.Recipe'),
        ),
        migrations.AlterUniqueTogether(
            name='procedure',
            unique_together=set([('recipe', 'num')]),
        ),
    ]
