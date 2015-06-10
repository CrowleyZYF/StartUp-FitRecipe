from base.views import BaseView
from .models import Label
from .serializers import LabelSerializer
# Create your views here.


class LabelList(BaseView):
    def get(self, request, format=None):
        r = Label.get_label_in_group()
        for k, v in r.iteritems():
            r[k] = [LabelSerializer(label).data for label in v]
        return self.success_response(r)
