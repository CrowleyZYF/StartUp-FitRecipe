#!/usr/bin/env python
# -*- coding: utf-8 -*-
from .models import Comment
from base.serializers import BaseSerializer
from accounts.serializers import AccountSerializer

class CommentSerializer(BaseSerializer):
    author = AccountSerializer(value=('id', 'avatar', 'nick_name', 'is_official'))
    reply = AccountSerializer(value=('id', 'avatar', 'nick_name', 'is_official'))

    class Meta:
        model = Comment
