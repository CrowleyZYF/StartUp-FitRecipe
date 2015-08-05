# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0003_article_wx_url'),
    ]

    operations = [
        migrations.AddField(
            model_name='article',
            name='recommend_img',
            field=models.URLField(null=True, verbose_name='\u63a8\u8350\u5c01\u9762', blank=True),
        ),
        migrations.AddField(
            model_name='series',
            name='img_cover',
            field=models.URLField(default='https://dn-wtbox.qbox.me/img//logo@2x.png', verbose_name='\u5c01\u9762'),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='series',
            name='introduce',
            field=models.TextField(default='ddd', verbose_name='\u7b80\u4ecb'),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='series',
            name='recommend_img',
            field=models.URLField(null=True, verbose_name='\u63a8\u8350\u5c01\u9762', blank=True),
        ),
    ]
