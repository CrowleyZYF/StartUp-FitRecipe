# Fit Recipe API

# Group Recipe

+ 菜谱名称
+ 菜谱图片
+ 烹饪时间
+ 作者（头像，名称，ID）
+ 菜谱简介
+ 功效标签： 增肌 减脂
+ 用餐时间标签： 早餐 加餐 正餐
+ 食材标签： 鸡肉 鱼肉 牛肉 海鲜 蛋奶 果蔬 米面 点心
+ 其他标签： 酸甜 等等
+ 卡路里数

+ 配料表
    + 食材名 大米
    + 重量 200克
    + 备注 约一小碗
+ 营养表
+ 菜谱步骤表
    + 步骤简述
    + 步骤配图


## Recipe Detail [/api/recipe/{id}/]

+ Parameters
    + id (number) - 菜谱的 ID

### Retrieve Recipe [GET]
获取指定 ID 的菜谱详细信息

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "id": 3,
                "meat_labels": [
                    {
                        "id": 7,
                        "name": "猪肉",
                        "type": "食材"
                    },
                    {
                        "id": 8,
                        "name": "面条",
                        "type": "食材"
                    }
                ],
                "time_labels": [
                    {
                        "id": 3,
                        "name": "早餐",
                        "type": "用餐时间"
                    }
                ],
                "effect_labels": [
                    {
                        "id": 5,
                        "name": "减脂",
                        "type": "功效"
                    }
                ],
                "other_labels": [],
                "component_set": [
                    {
                        "amount": 5,
                        "remark": "一个",
                        "ingredient": {
                            "id": 2,
                            "name": "鸡蛋"
                        }
                    }
                ],
                "procedure_set": [
                    {
                        "content": "随便煮一煮",
                        "num": 1,
                        "img": "http://b.36krcnd.com/nil_class/df446fa6-ef37-4b34-a435-885d2ccc543f/ch.jpg!slider"
                    }
                ],
                "nutrition_set": {
                    "钠": {
                        "amount": 8.3,
                        "unit": "mg"
                    },
                    "热量（卡路里）": {
                        "amount": 2.6,
                        "unit": "kcal"
                    },
                    "不饱和脂肪酸": {
                        "amount": 0.0,
                        "unit": "g"
                    },
                    "纤维素": {
                        "amount": 0.0,
                        "unit": "g"
                    },
                    "碳水化合物": {
                        "amount": 0.04,
                        "unit": "g"
                    },
                    "水": {
                        "amount": 4.38,
                        "unit": "g"
                    },
                    "维他命 D": {
                        "amount": 0.0,
                        "unit": "µg"
                    },
                    "脂类": {
                        "amount": 0.01,
                        "unit": "g"
                    },
                    "胆固醇": {
                        "amount": 0.0,
                        "unit": "mg"
                    },
                    "维他命 C": {
                        "amount": 0.0,
                        "unit": "mg"
                    },
                    "蛋白质": {
                        "amount": 0.55,
                        "unit": "g"
                    }
                },
                "author": {
                    "nick_name": "OxeNuNAefMxqQDMiyirejJhiUzfEsPeOdlOalmmKIFHkXzTvQSc509207812",
                    "id": 4,
                    "avatar": "http://tp2.sinaimg.cn/1937464505/180/5708528601/1"
                },
                "created_time": "2015-05-20 16:55:50",
                "updated_time": "2015-05-20 16:58:49",
                "img": "http://b.36krcnd.com/nil_class/df446fa6-ef37-4b34-a435-885d2ccc543f/ch.jpg!slider",
                "thumbnail": "http://b.36krcnd.com/nil_class/df446fa6-ef37-4b34-a435-885d2ccc543f/ch.jpg!slider",
                "title": "阳春面",
                "duration": 10,
                "calories": 2.6
            }
        }

+ Response 404 (application/json)

        {
            "detail": "Not found"
        }

## Recipe List [/api/recipe/list/?meat={meat}&effect={effect}&time={time}&start={start}&num={num}&order={order}&desc={desc}]

+ Parameters
    + meat: 1,2 (string, optional) - 食材 Label，多选使用逗号将 id 连接起来。如果不传则为全部。
    + effect: 3,4 (string, optional) - 功效 Label，多选也是一样，用逗号连接。不传为全部
    + time: 5,6 (string, optional) - 用餐时间 Label，不传为全部。
    + order: calories (string, optional) - 排序规则，默认按照卡路里。其他可选值：duration（烹饪时间），收藏数（暂不支持）
        + Default: calories
    + desc: false (string, optional) - 是否倒序，默认升序。若要倒序可以传字符串 true。
        + Default: false
    + start: 0 (string, optional) - 分页偏移。默认是 0。
        + Default: 0
    + num: 10 (string, optional) - 返回数量。默认 10 个。
        + Default: 10

### Retrieve all Recipes [GET]
获取所有的菜谱。列表中的菜谱不会包含营养表和步骤表。

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 2,
                    "meat_labels": [
                        {
                            "id": 7,
                            "name": "猪肉",
                            "type": "食材"
                        }
                    ],
                    "time_labels": [
                        {
                            "id": 6,
                            "name": "正餐",
                            "type": "用餐时间"
                        }
                    ],
                    "effect_labels": [
                        {
                            "id": 1,
                            "name": "增肌",
                            "type": "功效"
                        }
                    ],
                    "other_labels": [],
                    "author": {
                        "nick_name": "OxeNuNAefMxqQDMiyirejJhiUzfEsPeOdlOalmmKIFHkXzTvQSc509207812",
                        "id": 4,
                        "avatar": "http://tp2.sinaimg.cn/1937464505/180/5708528601/1"
                    },
                    "created_time": "2015-05-20 16:53:31",
                    "updated_time": "2015-05-20 16:53:31",
                    "img": "http://b.36krcnd.com/nil_class/df446fa6-ef37-4b34-a435-885d2ccc543f/ch.jpg!slider",
                    "thumbnail": "http://b.36krcnd.com/nil_class/df446fa6-ef37-4b34-a435-885d2ccc543f/ch.jpg!slider",
                    "title": "红烧肉",
                    "duration": 60,
                    "calories": 26.5
                },
                {
                    "id": 2,
                    "...":"..."
                }
            ]
        }

## Search [/api/recipe/search/?keyword={keyword}&start={start}&num={num}]

+ Parameters
    + keyword:猪 (string) - 搜索关键词
    + start:1 {number} - 偏移，默认 0
    + num:1 {number} - 返回数量，默认 10

### Search Recipes [GET]

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 3,
                    "meat_labels": [
                        {
                            "id": 11,
                            "name": "猪肉",
                            "type": "食材"
                        }
                    ],
                    "time_labels": [
                        {
                            "id": 9,
                            "name": "正餐",
                            "type": "用餐时间"
                        }
                    ],
                    "effect_labels": [
                        {
                            "id": 13,
                            "name": "减脂",
                            "type": "功效"
                        }
                    ],
                    "other_labels": [
                        {
                            "id": 14,
                            "name": "酸",
                            "type": "其他"
                        }
                    ],
                    "macro_element_ratio": "0:38:61",
                    "total_amount": "360g",
                    "protein_ratio": "38.38%",
                    "fat_ratio": "61.62%",
                    "author": null,
                    "created_time": "2015-05-28 15:40:04",
                    "updated_time": "2015-05-28 15:40:04",
                    "img": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "thumbnail": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "recommend_img": null,
                    "recommend_thumbnail": null,
                    "title": "猪肉干",
                    "introduce": null,
                    "tips": null,
                    "duration": 160,
                    "calories": 1043.4
                }
            ]
        }

# Group Label
分类标签，会分成这几类

- 用餐时间
- 功效
- 其他
- 食材

## Label List [/api/label/list]
### Retrieve all labels [GET]

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "用餐时间": [
                    {
                        "id": 8,
                        "name": "早餐",
                        "type": "用餐时间"
                    },
                    {
                        "id": 9,
                        "name": "正餐",
                        "type": "用餐时间"
                    }
                ],
                "功效": [
                    {
                        "id": 12,
                        "name": "增加",
                        "type": "功效"
                    },
                    {
                        "id": 13,
                        "name": "减脂",
                        "type": "功效"
                    }
                ],
                "其他": [
                    {
                        "id": 14,
                        "name": "酸",
                        "type": "其他"
                    }
                ],
                "食材": [
                    {
                        "id": 10,
                        "name": "鸡肉",
                        "type": "食材"
                    },
                    {
                        "id": 11,
                        "name": "猪肉",
                        "type": "食材"
                    }
                ]
            }
        }

# Group Recommend
首页的推荐逻辑。

- 按照后台设定的时间区间。用当前的时间（小时）去匹配。如果匹配多个则取第一个
- 如果这个时间段内没有推荐项目，则去所有推荐项目第一个。
- 如果一个推荐都没有，则返回空。所以请保证至少有一个推荐项目

## Retrieve Homepage Recommends [/api/recommend/]
### Retrieve Homepage Recommends By Time [GET]

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "theme": [
                    {"...": "..."}
                ],
                "update": [
                    {"...": "..."}
                ],
                "recommend": [
                    {"...": "..."}
                ]
            }
        }

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
- 微信地址

## Type List [/api/article/type/list]
### Retrieve article type list [GET]
获取文章类别的列表

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 1,
                    "created_time": "2015-08-02 15:14:43",
                    "updated_time": "2015-08-02 15:14:43",
                    "title": "test",
                    "introduce": "123123123123"
                }
            ]
        }

## Type Detail [/api/article/type/{id}]

+ Parameters
    + id: 1 (number) - 类别的 ID

### Retreive article type detail [GET]
获取文章列表的详情，包含其中的`系列`列表

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "id": 1,
                "series_set": [
                    {
                        "author_type": "test",
                        "author_avatar": "https://www.google.com.sg/webhp?hl=zh-CN",
                        "author": "123",
                        "id": 1,
                        "title": "test"
                    }
                ],
                "created_time": "2015-08-02 15:14:43",
                "updated_time": "2015-08-02 15:14:43",
                "title": "test",
                "introduce": "123123123123"
            }
        }

## Series List [/api/article/series/list?type={type}]

+ Parameters
    + type:1,2 {string} - 文章类型的id用逗号连接

### Retrieve Series List [GET]

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 2,
                    "created_time": "2015-08-05 23:11:16",
                    "updated_time": "2015-08-05 23:11:48",
                    "title": "cwec",
                    "introduce": "12deqwcewc",
                    "img_cover": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "recommend_img": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "author": "112e",
                    "author_avatar": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "author_type": "142",
                    "article_type": 2
                },
                {
                    "id": 3,
                    "created_time": "2015-08-05 23:11:16",
                    "updated_time": "2015-08-05 23:12:11",
                    "title": "cweca",
                    "introduce": "cesacaec",
                    "img_cover": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "recommend_img": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "author": "12",
                    "author_avatar": "https://dn-wtbox.qbox.me/img//logo@2x.png",
                    "author_type": "qwx",
                    "article_type": 2
                }
            ]
        }

## Series Detail [/api/article/series/{id}]

+ Parameters
    + id: 1 (number) - 系列的 ID

### Retreive series detail [GET]
获取系列的详情，包含其中的`文章`列表

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "id": 1,
                "article_set": [
                    {
                        "img_cover": "https://www.google.com.sg/webhp?hl=zh-CN",
                        "id": 1,
                        "title": "test"
                    }
                ],
                "created_time": "2015-08-02 15:14:46",
                "updated_time": "2015-08-02 15:14:46",
                "title": "test",
                "author": "123",
                "author_avatar": "https://www.google.com.sg/webhp?hl=zh-CN",
                "author_type": "test",
                "article_type": 1
            }
        }

## Article Detail [/api/article/{id}]

+ Parameters
    + id: 1 (number) - 文章的 ID

### Retreive article detail [GET]
获取文章详情

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "id": 1,
                "created_time": "2015-08-02 15:14:47",
                "updated_time": "2015-08-02 15:14:47",
                "title": "test",
                "img_cover": "https://www.google.com.sg/webhp?hl=zh-CN",
                "content": "dcewde",
                "wx_url": "https://code.djangoproject.com/ticket/12625",
                "series": 1
            }
        }

# Group Theme
## Theme List [/api/theme/list/]
### Retrieve Theme List [GET]
获取主题列表。目前返回所有列表。列表的筛选逻辑在下一次 pull request 里做掉。
主题列表里面不显示具体菜谱，只包含了菜谱数量。

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 1,
                    "created_time": "2015-05-28 15:40:04",
                    "updated_time": "2015-05-28 15:40:04",
                    "title": "只有庄奕峰会做的菜",
                    "content": "如果由黑暗料理大赛或者最难吃的菜大赛，庄奕峰一定能进决赛",
                    "img": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "thumbnail": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "recipe_count": 1
                },{
                    "...":"..."
                }
            ]
        }

## Theme Detail [/api/theme/{id}/]

+ Parameters
    + id: 1 (number) - 主题的id

### Retrieve Theme Detail [GET]
获取详细的主题内容，这里不会返回其中的菜谱，菜谱调用 Theme Recipes List 接口。

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": {
                "id": 1,
                "recipe_count": 1,
                "created_time": "2015-05-28 15:40:04",
                "updated_time": "2015-05-28 15:40:04",
                "title": "只有庄奕峰会做的菜",
                "content": "如果由黑暗料理大赛或者最难吃的菜大赛，庄奕峰一定能进决赛",
                "img": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                "thumbnail": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg"
            }
        }

## Theme Recipes List [/api/theme/{theme_id}/recipes/?start={start}&num={num}]

+ Parameters
    + id: 1 (number) - 主题的id
    + start: 0 (number, optional) - 分页的偏移
        + Default: 0
    + num: 5 (number, optional) - 返回结果数量
        + Default: 5

### Retrieve Recipes of specific Theme [GET]
获取某个专题下的菜谱列表。带分页

+ Response 200 (application/json)

        {
            "status": 200,
            "error_message": null,
            "data": [
                {
                    "id": 4,
                    "meat_labels": [],
                    "time_labels": [
                        {
                            "id": 8,
                            "name": "早餐",
                            "type": "用餐时间"
                        },
                        {
                            "id": 9,
                            "name": "正餐",
                            "type": "用餐时间"
                        }
                    ],
                    "effect_labels": [
                        {
                            "id": 12,
                            "name": "增加",
                            "type": "功效"
                        },
                        {
                            "id": 13,
                            "name": "减脂",
                            "type": "功效"
                        }
                    ],
                    "other_labels": [],
                    "author": {
                        "nick_name": "逗逼3",
                        "id": 4,
                        "avatar": "https://tower.im/assets/default_avatars/nightfall.jpg"
                    },
                    "created_time": "2015-05-28 15:40:04",
                    "updated_time": "2015-05-28 15:40:04",
                    "img": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "thumbnail": "http://d.36krcnd.com/nil_class/c9d8fd96-0159-4f3c-bcea-3f33203f46c9/__.jpg",
                    "title": "炒鸡蛋",
                    "duration": 10,
                    "calories": 156.0
                },{
                    "...":"..."
                }
            ]
        }

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
            "id": 5,
            "nick_name": "YERQe91343",
            "externals": [],
            "avatar": "http://tp2.sinaimg.cn/1937464505/180/5708528601/1",
            "is_changed_nick": false,
            "phone": "123",
            "token": "cdaf28dfba353a7704e9ebb2debb6f5bb2f93242"
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
            "id": 5,
            "nick_name": "YERQe91343",
            "externals": [],
            "avatar": "http://tp2.sinaimg.cn/1937464505/180/5708528601/1",
            "is_changed_nick": false,
            "phone": "123",
            "token": "cdaf28dfba353a7704e9ebb2debb6f5bb2f93242"
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
            "id": 5,
            "nick_name": "YERQe91343",
            "externals": [
                {"...":"..."}
            ],
            "avatar": "http://tp2.sinaimg.cn/1937464505/180/5708528601/1",
            "is_changed_nick": false,
            "phone": "123",
            "token": "cdaf28dfba353a7704e9ebb2debb6f5bb2f93242"
          }
        }
