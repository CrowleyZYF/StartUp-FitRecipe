# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0001_initial'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='articletype',
            options={'verbose_name': '\u7c7b\u522b', 'verbose_name_plural': '\u7c7b\u522b\u8868'},
        ),
        migrations.AlterField(
            model_name='article',
            name='series',
            field=models.ForeignKey(verbose_name='\u6240\u5c5e\u7cfb\u5217', to='article.Series'),
        ),
        migrations.AlterField(
            model_name='series',
            name='article_type',
            field=models.ForeignKey(verbose_name='\u6240\u5c5e\u7c7b\u522b', to='article.ArticleType'),
        ),
    ]
