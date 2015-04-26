# Fit Recipe Back End API Doc

Hello, here is the API document.

## Requires
- Python 2.7+
- Django 1.7.*
- djangorestframework 3.1

## Install
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

## API
# GET /api/recipes
List all recipes

+ Response 200 (application/json)

    [
        {
            "id": 1, 
            "created_time": "2015-04-26 07:32:29", 
            "updated_time": "2015-04-26 16:17:07", 
            "img_height": 2309, 
            "img_width": 3464, 
            "img": "static/images/DSC05069.jpg", 
            "thumbnail": "", 
            "title": "test123", 
            "type": 0, 
            "duration": "", 
            "calorie": ""
        }
    ]


