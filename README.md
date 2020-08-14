
介绍
spring-boot-keycloak


软件架构说明
springboot组织架构功能，组，用户，角色交于keycloak管理


安装教程

1. keycloak官网https://www.keycloak.org/documentation 下载9.0.2 版本
2. 安装目录windows下启动脚本./bin/standalone.bat -Djboss.socket.binding.port-offset=100
   http://localhost:8180/auth/ 登陆keycloak页面
   创建管理员账号admin  密码admin
3. keycloak管理页面添加域，直接导入项目的realm-import.json文件，域，用户，角色，client都创建完成
4. MyApplication启动项目，http://localhost:8089/swagger-ui.html查看相关接口







