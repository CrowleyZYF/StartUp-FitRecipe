import json
from random import Random

from rest_framework.authentication import TokenAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.authtoken.models import Token

from .models import Account


# Create your views here.
class LoginView(APIView):
    authentication_classes = (TokenAuthentication,)
    permission_classes = (IsAuthenticated,)

    def get(self, request, format=None):
        content = {
            'user': unicode(request.user),  # `django.contrib.auth.User` instance.
            'auth': unicode(request.auth),  # None
        }
        return Response(content)


class RegisterView(APIView):
    def random_str(scope=None, length=16):
        str = ''
        if scope == 'number':
            chars = '1234567890'
        elif scope == 'alphabet':
            chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz'
        else:
            chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'
        length = len(chars) - 1
        random = Random()
        for i in range(length):
            str += chars[random.randint(0, length)]
        return str

    def post(self, request, format=None):
        data = json.loads(request.body)
        external_source = data.get('external_source', None)
        insert_data = dict()
        if external_source:
            # third-party regist
            insert_data['username'] = '%s_%s%s' % (external_source, self.random_str(scope='alphabet', length=4), self.random_str(scope='number', length=8))
            insert_data['password'] = self.random_str()
            insert_data['phone'] = None
            insert_data['external_source'] = data.get('external_source')
            insert_data['external_id'] = data.get('external_source')
        else:
            # normal regist
            insert_data['username'] = data.get('username')
            insert_data['password'] = data.get('password')
            insert_data['phone'] = data.get('phone')
            insert_data['external_source'] = None
            insert_data['external_id'] = None
        u = Account.objects.create(**insert_data)
        token = Token.objects.create(user=u)
        return Response(token.key)
