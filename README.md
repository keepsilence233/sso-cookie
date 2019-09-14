#### SSO

sso(single sign on)单点登陆

##### 通过Cookie实现单点登陆

**单点登陆流程**

Cookie是在客户端存储数据的工具。

在其中一个子系统登陆，跳转到登陆系统，登陆系统完成登录，完成登陆后向发起登陆的子系统写入一个cookie，保存用于认证用户是否登陆的信息(token)，其他的子系统要能访问到这个cookie，在其他子系统向服务器发起请求的时候，携带这个cookie完成登陆。



**单点登录实现**

在windows的host文件中添加以下内容

#sso
127.0.0.1	www.codeshop.com  
127.0.0.1	login.codeshop.com  
127.0.0.1	vip.codeshop.com  
127.0.0.1	cart.codeshop.com  
