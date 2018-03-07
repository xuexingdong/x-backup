# x
五花八门，应有尽有

## common
常用工具类封装

## wechat
微信开发sdk

注：消息模式为加密模式时，需要下载JCE无限制权限策略文件

[JCE无限制权限策略文件下载地址JDK8版](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
(macOS最好用Safari下载，Chrome下载会导致文件无法正常解压)

下载后解压，可以看到local_policy.jar和US_export_policy.jar。
将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件即可。
