# Fit Recipe Back End API Doc

Hello, here is the API document.

## Requirements
- Python 2.7+
- Django 1.7.*
- djangorestframework 3.1

## Installation
1. create a new virtual enviroment with [virtualenv](https://github.com/pypa/virtualenv "virtualenv")
2. install requirements. `pip install -r requirements.txt`
2. get into `fitrecipe` folders.
2. make a copy of settings file. `cp settings.py.dev settings.py`
3. modify your own `settings.py` file, change `secret key` and other settings you want to change.
4. go back to the folders contains `manage.py`.
5. run `python manage.py syncdb`. If you like to create a superuser, you can make it done at this step.
6. run `python manage.py makemigrations`.
8. run `python manage.py migrate`.
9. run server now. `python manage.py runserver`. port 8000 is default

## API Documents
Read the document in [wiki](https://github.com/CrowleyZYF/fitRecipe/wiki/Back-End-API "wiki")

## CMS
After you started the server, you can access into the CMS.

Using the superuser account and password to login.

The superuser account is created when you ran `python manage.py syncdb` command.

If you didn't create the superuser, you can use `python manage.py createsuperuser` command to create one.

## Browsable API
You can access the APIV URL with your browser directly. If you want to turn off it, you can delete the following url config in `urls.py`. 

        url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework'))