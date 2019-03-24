# SimplePlugin
### 一个揭示插件化原理的项目，代码量少非常适合学习，仅用于学习插件化原理
## 项目特性：
1.启动插件中的Activity（不需要在AndroidManifest文件中注册）  
2.支持Android 8.0  
3.支持Intent传递参数

## 注意事项：
1.编译plugin项目作为插件apk并导入app项目assets文件夹  
2.如果assets文件夹发生过改动，请记得结束app进程，否则不会执行BaseApplication中的copy操作  
3.在插件Activity中需要使用Context的地方，请使用mProxyActivity代替  
4.在插件Activity中启动另一个插件Activity时，请使用BaseActivity中的startActivity(Intent intent, String className)方法
