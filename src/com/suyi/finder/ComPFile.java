package com.suyi.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class ComPFile {

	static HashMap map = new HashMap();
	static HashMap map1 = new HashMap();

	static HashMap map2 = new HashMap();
	static {

		map.put(-100, "圆");
		map.put(-99, "自定义");
		map.put(-98, "方");
		map.put(-97, "椭圆红色");
		map.put(-96, "椭圆橘红色");
		map.put(-95, "椭圆黄色");
		map.put(-94, "椭圆绿色");
		map.put(-93, "椭圆蓝色");
		map.put(-92, "椭圆蓝绿色");
		map.put(-91, "椭圆紫色");
		map.put(-90, "记忆键");
		
		map.put(0, "数字键0");
		map.put(1, "数字键1");
		map.put(2, "数字键2");
		map.put(3, "数字键3");
		map.put(4, "数字键4");
		map.put(5, "数字键5");
		map.put(6, "数字键6");
		map.put(7, "数字键7");
		map.put(8, "数字键8");
		map.put(9, "数字键9");
		
		map.put(800, "开关");
		map.put(801, "信号");
		map.put(802, "显示信息");
		map.put(803, "回看");
		map.put(804, "静音");
		map.put(805, "数位");
		map.put(806, "返回");
		map.put(807, "节目加");
		map.put(808, "节目减");
		map.put(809, "音量加");
		map.put(810, "音量减");
		map.put(811, "温度加");
		map.put(812, "温度减");
		map.put(813, "画面放大");
		map.put(814, "画面缩小");
		map.put(815, "记忆键1");
		map.put(816, "记忆键2");
		map.put(817, "确定");
		map.put(818, "向上");
		map.put(819, "向下");
		map.put(820, "向左");
		map.put(821, "向右");
		map.put(822, "菜单");
		map.put(823, "退出");
		map.put(824, "快进");
		map.put(825, "快退");
		map.put(826, "停/放");
		map.put(827, "停止");
		map.put(828, "回跳");
		map.put(829, "下个");
		map.put(830, "到顶");
		map.put(831, "到底");
		map.put(832, "模式");
		map.put(833, "风量");
		map.put(834, "水平风向");
		map.put(835, "垂直风向");
		map.put(836, "摇头");
		map.put(837, "风类");
		map.put(838, "风速");
		map.put(839, "open");
		map.put(840, "title");
		map.put(841, "ten_plus");
		map.put(842, "language");
		map.put(843, "screen");
		map.put(844, "sound_channel");
		map.put(845, "standard");
		map.put(846, "subtitles");
		map.put(847, "dual_screen");
		map.put(848, "画面冻结");
		map.put(849, "重置");
		map.put(850, "视频");
		map.put(851, "step_slow");
		map.put(852, "主键");
		map.put(853, "shutter_two");
		map.put(854, "continue_up");
		map.put(855, "continue_down");
		map.put(856, "continue_right");
		map.put(857, "continue_left");

		map1.put(-100, "base_round");
		map1.put(-99, "base_oval");
		map1.put(-98, "base_square");
		map1.put(-97, "base_oval_red");
		map1.put(-96, "base_oval_orange");
		map1.put(-95, "base_oval_yellow");
		map1.put(-94, "base_oval_green");
		map1.put(-93, "base_oval_blue");
		map1.put(-92, "base_oval_cyan");
		map1.put(-91, "base_oval_purple");
		map1.put(-90, "memorykey");
		map1.put(0, "zero ");
		map1.put(1, "one ");
		map1.put(2, "two ");
		map1.put(3, "three ");
		map1.put(4, "four ");
		map1.put(5, "five ");
		map1.put(6, "six ");
		map1.put(7, "seven ");
		map1.put(8, "eight ");
		map1.put(9, "nine ");
		map1.put(800, "power ");
		map1.put(801, "signal ");
		map1.put(802, "information ");
		map1.put(803, "look_back ");
		map1.put(804, "mute ");
		map1.put(805, "digital ");
		map1.put(806, "back ");
		map1.put(807, "channel_up ");
		map1.put(808, "channel_down ");
		map1.put(809, "vol_up ");
		map1.put(810, "vol_down ");
		map1.put(811, "temp_up ");
		map1.put(812, "temp_down ");
		map1.put(813, "d_zoom_up ");
		map1.put(814, "d_zoom_down ");
		map1.put(815, "memorykey_one ");
		map1.put(816, "memorykey_two ");
		map1.put(817, "menu_ok ");
		map1.put(818, "menu_up ");
		map1.put(819, "menu_down");
		map1.put(820, "menu_left");
		map1.put(821, "menu_right");
		map1.put(822, "menu");
		map1.put(823, "menu_exit");
		map1.put(824, "forward");
		map1.put(825, "rewind");
		map1.put(826, "pause");
		map1.put(827, "stop");
		map1.put(828, "previous");
		map1.put(829, "next");
		map1.put(830, "top");
		map1.put(831, "bottom");
		map1.put(832, "mode");
		map1.put(833, "wind_amount");
		map1.put(834, "wind_horizontal");
		map1.put(835, "wind_vertical");
		map1.put(836, "head_shaking");
		map1.put(837, "wind_class");
		map1.put(838, "wind_velocity");
		map1.put(839, "open");
		map1.put(840, "title");
		map1.put(841, "ten_plus");
		map1.put(842, "language");
		map1.put(843, "screen");
		map1.put(844, "sound_channel");
		map1.put(845, "standard");
		map1.put(846, "subtitles");
		map1.put(847, "dual_screen");
		map1.put(848, "freeze");
		map1.put(849, "reset");
		map1.put(850, "video");
		map1.put(851, "step_slow");
		map1.put(852, "shutter_one");
		map1.put(853, "shutter_two");
		map1.put(854, "continue_up");
		map1.put(855, "continue_down");
		map1.put(856, "continue_right");
		map1.put(857, "continue_left");

		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TreeSet set = new TreeSet();

		set.addAll(map1.keySet());

		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {

			Object key = iterator.next();

			// System.out.println("map1.put(" + key + ",     \"" + map.get(key)
			// + "\");");

			if (!map.containsKey(key)) {
				System.out.println("map.put(" + key + ",     \""
						+ map1.get(key) + "\");");
			}

			// System.out.println(key+"      "+map1.get(key)+"     "+map1.get(key));

			System.out.println("map3.put(" + key + ",new String[]{     \""
					+ map.get(key) + "\", \"" + map1.get(key) + "\"});");

		}

	}

}
