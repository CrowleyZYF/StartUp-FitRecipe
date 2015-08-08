#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from base.views import BaseView, AuthView
from .serializers import CommentSerializer
from .models import Comment
from accounts.models import Account
from recipe.models import Recipe

class CommentCreate(AuthView):
    def post(self, request, format=None):
        c = Comment()
        try:
            data = json.loads(request.body)
            reply_id = data.get('reply', None)
            c.reply= reply_id and Account.objects.get(pk=reply_id)
            c.content = data['content']
            c.author = Account.find_account_by_user(request.user)
            c.recipe = Recipe.objects.get(pk=data['recipe'])
        except:
            return self.fail_response(400, 'BadArgument')
        c.save()
        return self.success_response(CommentSerializer(c).data)


class CommentList(BaseView):
    def get(self, request, recipe_id, format=None):
        last_id = request.GET.get('lastid', None)
        r = Recipe.objects.get(pk=recipe_id)
        if last_id:
            comment_list = Comment.objects.filter(recipe=r, id__lt=last_id).order_by('-created_time')[0:20]
        else:
            comment_list = Comment.objects.filter(recipe=r).order_by('-created_time')[0:20]
        return self.success_response(CommentSerializer(comment_list, many=True).data)
