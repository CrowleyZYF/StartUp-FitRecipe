#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author: chaihaotian
# @Date:   2015-05-28 14:01:30
# @Last Modified by:   chaihaotian
# @Last Modified time: 2015-05-28 15:39:58
import os
import django
from django.conf import settings
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "fitrecipe.settings")

# settings.configure()
django.setup()

from django.db import transaction
from accounts.models import Account
from recipe.models import *
from label.models import Label
from theme.models import Theme

with transaction.atomic():
    a = Account.objects.create(nick_name=u'逗逼3', username=u'doubiya3', password=u'123456', phone=u'18611470903', avatar=u'https://tower.im/assets/default_avatars/nightfall.jpg')

    i1 = Ingredient.objects.create(name=u'鸡蛋', ndbno=u'01124')
    i2 = Ingredient.objects.create(name=u'猪肉', ndbno=u'10898')
    i3 = Ingredient.objects.create(name=u'油', ndbno=u'04678')

    l1 = Label.objects.create(name=u'早餐', type=u'用餐时间')
    l2 = Label.objects.create(name=u'正餐', type=u'用餐时间')
    l3 = Label.objects.create(name=u'鸡肉', type=u'食材')
    l4 = Label.objects.create(name=u'猪肉', type=u'食材')
    l5 = Label.objects.create(name=u'增加', type=u'功效')
    l6 = Label.objects.create(name=u'减脂', type=u'功效')
    l7 = Label.objects.create(name=u'酸', type=u'其他')

    r = Recipe.objects.create(author=a, img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', thumbnail=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', title=u'红烧肉', duration=u'30')
    r.effect_labels.add(l5)
    r.time_labels.add(l1)
    r.meat_labels.add(l3)
    r.meat_labels.add(l4)
    Component.objects.create(recipe=r, ingredient=i2, amount=100, remark=u'一块')
    Component.objects.create(recipe=r, ingredient=i3, amount=20, remark=u'一勺')
    Procedure.objects.create(recipe=r, num=1, content=u'第一步你说做什么', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')
    Procedure.objects.create(recipe=r, num=2, content=u'叫外卖呗', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')

    r = Recipe.objects.create(author=a, img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', thumbnail=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', title=u'红烧鸡块', duration=u'20')
    r.effect_labels.add(l6)
    r.time_labels.add(l2)
    r.meat_labels.add(l4)
    Component.objects.create(recipe=r, ingredient=i1, amount=100, remark=u'一块')
    Component.objects.create(recipe=r, ingredient=i3, amount=10, remark=u'一勺')
    Procedure.objects.create(recipe=r, num=1, content=u'切一切', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')
    Procedure.objects.create(recipe=r, num=2, content=u'抄一抄', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')

    r = Recipe.objects.create(author=a, img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', thumbnail=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', title=u'猪肉干', duration=u'160')
    r.effect_labels.add(l6)
    r.time_labels.add(l2)
    r.other_labels.add(l7)
    r.meat_labels.add(l4)
    Component.objects.create(recipe=r, ingredient=i2, amount=300, remark=u'三块')
    Component.objects.create(recipe=r, ingredient=i3, amount=60, remark=u'很多')
    Procedure.objects.create(recipe=r, num=1, content=u'这玩意你还是去超市买好', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')
    Procedure.objects.create(recipe=r, num=2, content=u'真的', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')

    r = Recipe.objects.create(author=a, img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', thumbnail=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', title=u'炒鸡蛋', duration=u'10')
    r.effect_labels.add(l6)
    r.effect_labels.add(l5)
    r.time_labels.add(l1)
    r.time_labels.add(l2)
    Component.objects.create(recipe=r, ingredient=i1, amount=300, remark=u'三个')
    Procedure.objects.create(recipe=r, num=1, content=u'炒鸡蛋还不会怎么找女朋友', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')
    Procedure.objects.create(recipe=r, num=2, content=u'怪不得你没有女朋友', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')

    t = Theme.objects.create(title=u'只有庄奕峰会做的菜', content=u'如果由黑暗料理大赛或者最难吃的菜大赛，庄奕峰一定能进决赛', img=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg', thumbnail=u'http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg')
    t.recipes.add(r)
