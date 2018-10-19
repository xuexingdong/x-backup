# Project X

个人项目

五花八门，应有尽有

## common
公共模块

### entity
业务实体

### util
工具类 

## parent
父项目

## services
具体服务

### service-parent
服务父pom

### backend
主后端

### crawler
爬虫项目后台

目前支持以下爬虫
1. 天猫商品价格监控


## chatbot
个人微信机器人

## wechat
微信公众平台

注：消息模式为加密模式时，需要下载JCE无限制权限策略文件

[JCE无限制权限策略文件下载地址JDK8版](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
(macOS最好用Safari下载，Chrome下载会导致文件无法正常解压)

下载后解压，可以看到local_policy.jar和US_export_policy.jar。
将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件即可。

