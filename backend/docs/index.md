# Fit Recipe API

# Group Recipe
菜谱

菜谱名称
菜谱图片
烹饪时间 
作者 
菜谱简介

标签 (功效) 增肌 减脂 (用餐时间) 早餐 加餐 正餐 (食材) 鸡肉 鱼肉 牛肉 海鲜 蛋奶 果蔬 米面 点心 (其他标签) 酸甜 等等

配料
营养表
菜谱步骤表




配料
食材名 大米
重量 200克
备注 约一小碗


营养表 待定



步骤表
步骤简述
步骤配图
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

# Group Knowledge
知识体系中的数据结构是这样的：

- 最大的概念是 `类别`，例如：减脂类，增肌类，小提示
- `类别` 中会包含多个 `系列`，例如： 增肌类下面会有斌卡系列、陈柏霖系列等
- 每个 `系列` 里会有多个 `文章`

## 类别的数据结构
- 名字
- 描述

## 系列的数据结构
- 名字
- 作者
- 作者头像
- 作者类型
- 阅读数
- 关注数

## 文章的数据结构
- 标题
- 封面
- 阅读人数
- 评论数
- 转发数
- 发布时间
- 内容


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
