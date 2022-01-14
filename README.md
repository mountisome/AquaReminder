## Aqua Reminder

### 1.简要介绍

这是我们人机交互课程的大作业，任务是设计一个简单的app，要求符合人机交互目标。我们设计了一个喝水提醒app—Aqua Reminder，该app是模仿柠檬喝水app实现的。功能有**每日喝水量显示**，**喝水量添加**，**喝水提醒**，**种植树木**，**用户排名**等。app还有很多不足之处可以改进，欢迎评论和pr。



### 2.技术

- 平台：Android Studio
- 语言：java



### 3.功能介绍

- 每日喝水量显示：
    - 喝水的水杯中会显示今日的喝水量
    - 水杯中的水量不会超过1500ml
- 喝水量添加：
    - 喝完水后可以添加本次喝水量
    - 一天过去将会更新喝水量
- 喝水提醒：
    - 定时提醒：闹钟将会在规定的时间提醒
    - 间隔提醒：闹钟将在设置后每隔一小时提醒
- 种植树木：
    - 用户可以在种植界面兑换树木种子
    - 兑换后树木可以在已种植中查看
- 用户排名：
    - app将会按照能量多少将所有用户排名



### 4.主要页面展示

登录界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995160.png"></div>

喝水界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995173.png"></div>

添加喝水量界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995177.png"></div>

提醒设置界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995183.png"></div>

种植树木界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1642075959.png"></div>

选择树木界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995189.png"></div>

已种植界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995193.png"></div>

用户界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1642075991.png"></div>

用户排名界面：

<div align=center><img src="https://gitee.com/mountisome/images/raw/master/img/Screenshot_1641995391.png"></div>



### 5.使用教程

- 根据项目中sql文件夹下的AquaReminder.sql建好数据库
- 修改utils包下的DBUtils中的url，将ip地址修改为自己本机的ip地址，否则连接数据库失败
- 在Android Studio中下载相关的AVD运行环境，我用的是`Nexus 6P API 25`
- 运行项目