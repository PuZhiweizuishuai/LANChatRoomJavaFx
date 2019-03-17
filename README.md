# 基于JavaFX的网络聊天软件

## 运行环境
Java8

## 项目目录
项目目录
客户端部分
ChatMessage
    communication    包含网络通信模块
    history            历史记录界面
    Login             登陆界面
    Main              聊天主界面
    Setting            设置界面
    SignUp            注册界面
    user              用户数据部分
com             消息提醒的动画部分，来自https://github.com/DomHeal/JavaFX-Chat
    traynotifications
Data                保存部分数据，如服务器ip
lib                  依赖jar包
mycontrol             自定义控件
    chatbox           聊天气泡
    popup            弹窗
    userlist            左侧用户列表
resources              资源文件
    fxml               JavaFx的界面文件
    images             图片资源
        head           预置头像
    sounds             声音文件
SettingServer            设置服务器信息
    
服务端部分
ChatMessage
exception           自定义异常
user               用户数据
DB                     数据库操作模块
lib                     依赖
MainUI                 设置界面
popup                 弹窗
Server                 服务器主程序


## 程序运行截图
<img src="">

## 已知问题
服务器端的一键创建数据库无法使用
聊天界面性能有待提升
多线程没有使用线程池

## 待更新
文件传输
图片传输
以及聊天记录的保存