# mvn-TestPaperGenerator
HNU结队编程

---
本项目前后端分离，前端由**唐典**同学100%制作，后端由**兰凯伦**同学100%制作

后端使用了javaweb框架，mybatis管理数据库，maven管理项目，并采用了MVC的管理模式，运行效果良好

前端技术为基本的html语言加上js代码；在网页设计时采用了semantic ui进行辅助设计，自然也加入jQuery框架。form表单提交由于接口方法和form表单的兼容问题，全部改为了click后调用js函数，使用ajax跳转。

项目完全开源，点击这里获取[项目源码](https://github.com/KallenBlue/mvn-TestPaperGenerator)

## 使用配置
### 1.tomcat
项目使用的tomcat的版本为9.0.65，如果运行异常请检查tomcat版本
### 2.mybatis-config.xml文件配置
文件路径为：src/main/resources/mybatis-config.xml
配置为自己的数据库信息即可

本项目仅使用user table，ddl放在下面了
~~~sql
create table user
(
    account  varchar(12) not null
        primary key,
    password varchar(15) null,
    identity varchar(5)  null
);
~~~
需要先创建数据库才能正常运行
### 3.建议
建议使用idea运行此项目
## 其他
好像其他没啥了，想到了再加
