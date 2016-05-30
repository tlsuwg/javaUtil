package com.example.com.suyi.domain;

import java.util.HashMap;

/**
*
*/

import java.util.HashMap;

/**
*@author"suwg"2016年5月30日
*/
public class PermissionName{

	static HashMap<String,String> nameMap=new HashMap<>();
	static{
		nameMap.put("android.permission.ACCESS_CHECKIN_PROPERTIES","﻿访问登记属性");
		nameMap.put("android.permission.ACCESS_COARSE_LOCATION","获取错略位置");
		nameMap.put("android.permission.ACCESS_FINE_LOCATION","获取精确位置");
		nameMap.put("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",
				"访问定位额外命令");
		nameMap.put("android.permission.ACCESS_MOCK_LOCATION","获取模拟定位信息");
		nameMap.put("android.permission.ACCESS_NETWORK_STATE","获取网络状态");
		nameMap.put("android.permission.ACCESS_SURFACE_FLINGER",
				"访问SurfaceFlinger");
		nameMap.put("android.permission.ACCESS_WIFI_STATE","获取WiFi状态");
		nameMap.put("android.permission.ACCOUNT_MANAGER","账户管理");
		nameMap.put("android.permission.AUTHENTICATE_ACCOUNTS","验证账户");
		nameMap.put("android.permission.BATTERY_STATS","电量统计");
		nameMap.put("android.permission.BIND_APPWIDGET","绑定小插件");
		nameMap.put("android.permission.BIND_DEVICE_ADMIN","绑定设备管理");
		nameMap.put("android.permission.BIND_INPUT_METHOD","绑定输入法");
		nameMap.put("android.permission.BIND_REMOTEVIEWS","绑定RemoteView");
		nameMap.put("android.permission.BIND_WALLPAPER","绑定壁纸");
		nameMap.put("android.permission.BLUETOOTH","使用蓝牙");
		nameMap.put("android.permission.BLUETOOTH_ADMIN","蓝牙管理");
		nameMap.put("android.permission.BRICK","变成砖头");
		nameMap.put("android.permission.BROADCAST_PACKAGE_REMOVED","应用删除时广播");
		nameMap.put("android.permission.BROADCAST_SMS","收到短信时广播");
		nameMap.put("android.permission.BROADCAST_STICKY","连续广播");
		nameMap.put("android.permission.BROADCAST_WAP_PUSH","WAPPUSH广播");
		nameMap.put("android.permission.CALL_PHONE","拨打电话");
		nameMap.put("android.permission.CALL_PRIVILEGED","通话权限");
		nameMap.put("android.permission.CAMERA","拍照权限");
		nameMap.put("android.permission.CHANGE_COMPONENT_ENABLED_STATE",
				"改变组件状态");
		nameMap.put("android.permission.CHANGE_CONFIGURATION","改变配置");
		nameMap.put("android.permission.CHANGE_NETWORK_STATE","改变网络状态");
		nameMap.put("android.permission.CHANGE_WIFI_MULTICAST_STATE",
				"改变WiFi多播状态");
		nameMap.put("android.permission.CHANGE_WIFI_STATE","改变WiFi状态");
		nameMap.put("android.permission.CLEAR_APP_CACHE","清除应用缓存");
		nameMap.put("android.permission.CLEAR_APP_USER_DATA","清除用户数据");
		nameMap.put("android.permission.CWJ_GROUP","底层访问权限");
		nameMap.put("android.permission.CELL_PHONE_MASTER_EX","手机优化大师扩展权限");
		nameMap.put("android.permission.CONTROL_LOCATION_UPDATES","控制定位更新");
		nameMap.put("android.permission.DELETE_CACHE_FILES","删除缓存文件");
		nameMap.put("android.permission.DELETE_PACKAGES","删除应用");
		nameMap.put("android.permission.DEVICE_POWER","电源管理");
		nameMap.put("android.permission.DIAGNOSTIC","应用诊断");
		nameMap.put("android.permission.DISABLE_KEYGUARD","禁用键盘锁");
		nameMap.put("android.permission.DUMP","转存系统信息");
		nameMap.put("android.permission.EXPAND_STATUS_BAR","状态栏控制");
		nameMap.put("android.permission.FACTORY_TEST","工厂测试模式");
		nameMap.put("android.permission.FLASHLIGHT","使用闪光灯");
		nameMap.put("android.permission.FORCE_BACK","强制后退");
		nameMap.put("android.permission.GET_ACCOUNTS","访问账户Gmail列表");
		nameMap.put("android.permission.GET_PACKAGE_SIZE","获取应用大小");
		nameMap.put("android.permission.GET_TASKS","获取任务信息");
		nameMap.put("android.permission.GLOBAL_SEARCH","允许全局搜索");
		nameMap.put("android.permission.HARDWARE_TEST","硬件测试");
		nameMap.put("android.permission.INJECT_EVENTS","注射事件");
		nameMap.put("android.permission.INSTALL_LOCATION_PROVIDER","安装定位提供");
		nameMap.put("android.permission.INSTALL_PACKAGES","安装应用程序");
		nameMap.put("android.permission.INTERNAL_SYSTEM_WINDOW","内部系统窗口");
		nameMap.put("android.permission.INTERNET","访问网络");
		nameMap.put("android.permission.KILL_BACKGROUND_PROCESSES","结束后台进程");
		nameMap.put("android.permission.MANAGE_ACCOUNTS","管理账户");
		nameMap.put("android.permission.MANAGE_APP_TOKENS","管理程序引用");
		nameMap.put("android.permission.MTWEAK_USER","高级权限");
		nameMap.put("android.permission.MTWEAK_FORUM","社区权限");
		nameMap.put("android.permission.MASTER_CLEAR","软格式化");
		nameMap.put("android.permission.MODIFY_AUDIO_SETTINGS","修改声音设置");
		nameMap.put("android.permission.MODIFY_PHONE_STATE","修改电话状态");
		nameMap.put("android.permission.MOUNT_FORMAT_FILESYSTEMS","格式化文件系统");
		nameMap.put("android.permission.MOUNT_UNMOUNT_FILESYSTEMS","挂载文件系统");
		nameMap.put("android.permission.NFC","允许NFC通讯");
		nameMap.put("android.permission.PERSISTENT_ACTIVITY","永久Activity");
		nameMap.put("android.permission.PROCESS_OUTGOING_CALLS","处理拨出电话");
		nameMap.put("android.permission.READ_CALENDAR","读取日程提醒");
		nameMap.put("android.permission.READ_CONTACTS","读取联系人");
		nameMap.put("android.permission.READ_FRAME_BUFFER","屏幕截图");
		nameMap.put("com.android.browser.permission.READ_HISTORY_BOOKMARKS",
				"读取收藏夹和历史记录");
		nameMap.put("android.permission.READ_INPUT_STATE","读取输入状态");
		nameMap.put("android.permission.READ_LOGS","读取系统日志");
		nameMap.put("android.permission.READ_PHONE_STATE","读取电话状态");
		nameMap.put("android.permission.READ_SMS","读取短信内容");
		nameMap.put("android.permission.READ_SYNC_SETTINGS","读取同步设置");
		nameMap.put("android.permission.READ_SYNC_STATS","读取同步状态");
		nameMap.put("android.permission.REBOOT","重启设备");
		nameMap.put("android.permission.RECEIVE_BOOT_COMPLETED","开机自动允许");
		nameMap.put("android.permission.RECEIVE_MMS","接收彩信");
		nameMap.put("android.permission.RECEIVE_SMS","接收短信");
		nameMap.put("android.permission.RECEIVE_WAP_PUSH","接收WapPush");
		nameMap.put("android.permission.RECORD_AUDIO","录音");
		nameMap.put("android.permission.REORDER_TASKS","排序系统任务");
		nameMap.put("android.permission.RESTART_PACKAGES","结束系统任务");
		nameMap.put("android.permission.SEND_SMS","发送短信");
		nameMap.put("android.permission.SET_ACTIVITY_WATCHER","设置Activity观察其");
		nameMap.put("com.android.alarm.permission.SET_ALARM","设置闹铃提醒");
		nameMap.put("android.permission.SET_ALWAYS_FINISH","设置总是退出");
		nameMap.put("android.permission.SET_ANIMATION_SCALE","设置动画缩放");
		nameMap.put("android.permission.SET_DEBUG_APP","设置调试程序");
		nameMap.put("android.permission.SET_ORIENTATION","设置屏幕方向");
		nameMap.put("android.permission.SET_PREFERRED_APPLICATIONS","设置应用参数");
		nameMap.put("android.permission.SET_PROCESS_LIMIT","设置进程限制");
		nameMap.put("android.permission.SET_TIME","设置系统时间");
		nameMap.put("android.permission.SET_TIME_ZONE","设置系统时区");
		nameMap.put("android.permission.SET_WALLPAPER","设置桌面壁纸");
		nameMap.put("android.permission.SET_WALLPAPER_HINTS","设置壁纸建议");
		nameMap.put("android.permission.SIGNAL_PERSISTENT_PROCESSES",
				"发送永久进程信号");
		nameMap.put("android.permission.STATUS_BAR","状态栏控制");
		nameMap.put("android.permission.SUBSCRIBED_FEEDS_READ","访问订阅内容");
		nameMap.put("android.permission.SUBSCRIBED_FEEDS_WRITE","写入订阅内容");
		nameMap.put("android.permission.SYSTEM_ALERT_WINDOW","显示系统窗口");
		nameMap.put("android.permission.UPDATE_DEVICE_STATS","更新设备状态");
		nameMap.put("android.permission.USE_CREDENTIALS","使用证书");
		nameMap.put("android.permission.USE_SIP","使用SIP视频");
		nameMap.put("android.permission.VIBRATE","使用振动");
		nameMap.put("android.permission.WAKE_LOCK","唤醒锁定");
		nameMap.put("android.permission.WRITE_APN_SETTINGS","写入GPRS接入点设置");
		nameMap.put("android.permission.WRITE_CALENDAR","写入日程提醒");
		nameMap.put("android.permission.WRITE_CONTACTS","写入联系人");
		nameMap.put("android.permission.WRITE_EXTERNAL_STORAGE","写入外部存储");
		nameMap.put("android.permission.WRITE_GSERVICES","写入Google地图数据");
		nameMap.put("com.android.browser.permission.WRITE_HISTORY_BOOKMARKS",
				"写入收藏夹和历史记录");
		nameMap.put("android.permission.WRITE_SECURE_SETTINGS","读写系统敏感设置");
		nameMap.put("android.permission.WRITE_SETTINGS","读写系统设置");
		nameMap.put("android.permission.WRITE_SMS","编写短信");
		nameMap.put("android.permission.WRITE_SYNC_SETTINGS","写入在线同步设置");
	}

	public static String getName(String name){
		if(nameMap.containsKey(name))
			return nameMap.get(name);

		return name;
	}

}
