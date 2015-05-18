# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0003_account_avater'),
        ('recipe', '0007_auto_20150515_1927'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='component',
            options={'verbose_name': '\u6784\u6210', 'verbose_name_plural': '\u6784\u6210\u8868'},
        ),
        migrations.AlterModelOptions(
            name='ingredient',
            options={'verbose_name': '\u539f\u6599', 'verbose_name_plural': '\u539f\u6599\u8868'},
        ),
        migrations.AlterModelOptions(
            name='label',
            options={'verbose_name': '\u6807\u7b7e', 'verbose_name_plural': '\u6807\u7b7e\u8868'},
        ),
        migrations.AlterModelOptions(
            name='nutrition',
            options={'verbose_name': '\u8425\u517b', 'verbose_name_plural': '\u8425\u517b\u8868'},
        ),
        migrations.AlterModelOptions(
            name='procedure',
            options={'verbose_name': '\u6b65\u9aa4', 'verbose_name_plural': '\u6b65\u9aa4\u8868'},
        ),
        migrations.AlterModelOptions(
            name='recipe',
            options={'verbose_name': '\u83dc\u8c31', 'verbose_name_plural': '\u83dc\u8c31\u8868'},
        ),
        migrations.AddField(
            model_name='recipe',
            name='author',
            field=models.ForeignKey(default=1, to='accounts.Account'),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name='label',
            name='name',
            field=models.CharField(max_length=25, verbose_name='\u6807\u7b7e\u540d\u79f0'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='label',
            name='type',
            field=models.CharField(max_length=25, verbose_name='\u6807\u7b7e\u7c7b\u578b', choices=[('\u529f\u6548', '\u529f\u6548'), ('\u7528\u9910\u65f6\u95f4', '\u7528\u9910\u65f6\u95f4'), ('\u98df\u6750', '\u98df\u6750'), ('\u5176\u4ed6', '\u5176\u4ed6')]),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='duration',
            field=models.IntegerField(help_text=b'\xe5\x88\x86\xe9\x92\x9f', verbose_name='\u70f9\u996a\u65f6\u95f4'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='effect_labels',
            field=models.ManyToManyField(related_name='effect_set', null=True, verbose_name='\u529f\u6548\u6807\u7b7e', to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='img',
            field=models.URLField(verbose_name='\u5927\u56fe URL'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='meat_labels',
            field=models.ManyToManyField(related_name='meat_set', null=True, verbose_name='\u98df\u6750\u6807\u7b7e', to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='other_labels',
            field=models.ManyToManyField(related_name='other_set', null=True, verbose_name='\u5176\u4ed6\u6807\u7b7e', to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='thumbnail',
            field=models.URLField(verbose_name='\u7f29\u7565\u56fe URL'),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='time_labels',
            field=models.ManyToManyField(related_name='time_set', null=True, verbose_name='\u7528\u9910\u65f6\u95f4\u6807\u7b7e', to='recipe.Label', blank=True),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='recipe',
            name='title',
            field=models.CharField(max_length=100, verbose_name='\u83dc\u8c31\u540d\u79f0'),
            preserve_default=True,
        ),
    ]
