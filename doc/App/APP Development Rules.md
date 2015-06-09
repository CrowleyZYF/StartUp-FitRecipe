# 安卓开发规范 #
----------
## 目录 ##

1. XML命名规范
2. 文件目录结构






## XML命名规范 ##
**1. 文件名命名规范**

- 规范：命名主要由五部分组成，全小写，首先是，activity或fragment或framework，表明是用在哪的；其次是，所属功能块，比如指南knowledge，计划plan；接着是，在功能块中的作用或部分，比如是计划下的营养表nutrition；然后是，是否为上一个的部分子元素，例如是计划下的营养表中的子项item，此项可选；最后是，某种状态，例如计划下的营养表中的子项item被点击后的状态，此项可选。
- 正则：`[activity|fragment|framework]_[index|plan|knowledge|me|recipe|category|search]_[SomeFunction|SomePart]*_[ChildElement]?_[State]?`
- 例子：`fragment_plan_nutrition_item_click.xml`

**2. 控件ID命名规范**

- 规范：命名主要由两部分组成，全小写，首先是，在该界面下，功能和作用描述；最后是控件名称
- 正则：`[SomeFunction|SomePart]*_[layout|btn|menu|check|gridview|text]?`
- 例子：`back_btn`

## 文件目录结构 ##

**1. 根目录**

- WelcomeActivity：欢迎页。
	- 加载首页json数据，并进行图片资源缓存，最长停留时间为2000毫秒
	- 检测是否用户已登录。如果登陆，跳转至首页MainActivity；如果未登陆，跳转至介绍页LandingPageActivity
- LandingPageActivity：介绍页
	- 提供登陆、注册、直接进入主页的入口
- MainActivity：主界面
	- 四个Tab页，每个tab页为一个fragment