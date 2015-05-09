# Fit Recipe API

# Group Recipe
## Recipe Detail [/api/recipe/{id}/]

+ Parameters
    + id (number) - 菜谱的 ID

### Retrieve Recipe [GET]
获取指定 ID 的菜谱详细信息

+ Response 200 (application/json)

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

+ Response 404 (application/json)

        {
            "detail": "Not found"
        }

## Recipe List [/api/recipes/]
__这个 URL 要改的__

### Retrieve all Recipes [GET]
获取所有的菜谱

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
            },
            {
                "id": 2,
                "...":"..."
            }
        ]


# Group Authorization
我们使用 Token 的验证方式，保证 `https` 访问所有接口。

在需要认证的 Request Headers 中加入 `Authorization` 头， 它的值是 `Token {your-token-here}`。注意，是 `Token` 开头，后面跟着一个 `空格`，然后再加上登录注册时返回的 `Token`.

登录注册和第三方登录不用带着 Token
## Register [/api/accounts/register/]
### Normal Account Register [POST] 
注册帐号
正常的注册，需要密码和手机号

+ Request (application/json)

        {
            "password":"123",
            "phone":"123"
        }

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "token": "156c6c47e4ef408ff494513c755a3c42881428c0"
            }
        }

+ Response 400 (application/json)

        {
            "status": 400, 
            "error_message": "Phone registed",
            "data": null
        }

## Login [/api/accounts/login/]
### Normal Account Login [POST] 
正常帐号登录

+ Request (application/json)

        {
            "password":"123",
            "phone":"123"
        }

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "token": "156c6c47e4ef408ff494513c755a3c42881428c0"
            }
        }

+ Response 401 (application/json)

        {
            "status": 401, 
            "error_message": "password is not correct",
            "data": null
        }

+ Response 402 (application/json)

        {
            "status": 402, 
            "error_message": "Account not existed",
            "data": null
        }


## Third-Party Login [/api/accounts/thirdparty/]
### Third-Party Login & Register [POST]
第三方帐号登录。后面除了 `nick_name` 可能还需要类似头像之类的。

+ Request (application/json)

        {
            "nick_name":"123",
            "external_source":"test",
            "external_id":"12344"
        }

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "token": "156c6c47e4ef408ff494513c755a3c42881428c0"
            }
        }
