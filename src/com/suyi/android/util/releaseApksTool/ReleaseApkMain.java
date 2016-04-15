package com.suyi.android.util.releaseApksTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ReleaseApkMain {

	static HashMap<Integer, String> map = new HashMap<Integer, String>();

	static {
		map.put(199, "201305/193068b5-d365-4578-947c-4692fb6f692e");
		map.put(200, "201305/41110444-81e1-4653-9527-9e5a7ecadf58");
		map.put(201, "201305/c124b265-a927-4ef5-b69e-628ca8139eda");
		map.put(205, "201305/96e30e73-f3c1-4abe-a6bc-b5a051a7d31f");
		map.put(204, "201305/0955f4e0-fcbd-4a2d-baf0-d152a64bc8ea");
		map.put(203, "201305/7fd5d4f7-45d7-481f-bb5e-c910ebda255c");
		map.put(202, "201305/24625c4c-c17f-4a2c-bbe3-cf9cb0a17ab7");
		map.put(209, "201305/c636e562-32ef-4665-91e8-a55861faf55b");
		map.put(210, "201305/8afaa89e-a0f8-4470-8b9a-21bd572ebd74");
		map.put(211, "201305/31e032ab-c771-49fd-816d-86d567ca1ba4");
		map.put(212, "201305/7e9554b9-dce8-4ff0-980c-103eb4df6487");
		map.put(213, "201305/c5e97b65-e468-4ed1-9e8b-340ff61a2012");
		map.put(214, "201305/a676eeae-6ce1-4178-8199-852ef614553c");
		map.put(215, "201305/8495f2ec-61d2-47be-834a-bdab43a32818");
		map.put(216, "201305/2ea6d99a-1932-4d9f-96db-83a2de392ab2");
		map.put(217, "201305/d74894a4-6c63-435d-b236-5d885ca3840c");
		map.put(218, "201305/2d104951-5164-4d02-9bc7-77c9fd2ed306");
		map.put(219, "201305/7635a86a-52b5-47d1-8c0f-0a4367e18ac4");
		map.put(220, "201305/fb8dd4dc-bfcb-43aa-9fd5-3f9e2bb67443");
		map.put(206, "201305/a1276a85-8a44-469b-abc7-1936403c8fb6");
		map.put(207, "201305/6a12881e-4e6f-4f21-8e04-9a382058bc99");
		map.put(208, "201305/b3487b67-bd23-4631-9a57-2d5e6a2329e3");
		map.put(225, "201305/e7183f36-c257-43a5-ba54-3236e69500e2");
		map.put(221, "201305/3602f94e-0647-4d23-8efc-01338ba2aeb5");
		map.put(224, "201305/9bcf55e2-b27f-468e-9ea4-c396376cc7bb");
		map.put(222, "201305/ed5d1677-5e33-44df-8ac4-8ea3cad0403f");
		map.put(223, "201305/80e7b79c-12ba-466e-97e3-97f10a0b799e");
		map.put(229, "201305/9da42fbe-c35a-4599-942c-271ce59e0c6a");
		map.put(227, "201306/fb80e015-39ac-4826-b3ae-791ec3bf9e12");
		map.put(226, "201306/8b7c6cbc-da26-4b61-a7dd-a6fb91fd27e2");
		map.put(228, "201306/dae1fe25-65c3-4a82-89d4-9a29d6dac2de");
		map.put(230, "201306/a30ecfcf-82b9-4d8c-8522-b9700bcb254e");
		map.put(231, "201306/70869382-ccbe-4ca0-9304-91c73541c4c9");
		map.put(232, "201306/9794fc1a-f77a-4760-bf06-993380ee34b9");
		map.put(250, "201306/cc0bc9f6-1017-4012-acd9-dc4d243f772f");
		map.put(249, "201306/4743e399-1b16-4d9c-81c2-69f327930844");
		map.put(248, "201306/e3361328-e50e-4770-9ad7-ff9a06055fbb");
		map.put(247, "201306/50895fe0-bda9-421e-92d3-72eee310fd9a");
		map.put(246, "201306/d5c97627-e5bb-4703-ab04-f4e020e62266");
		map.put(245, "201306/743a0d4c-037d-4bda-8a38-b151bb57d67e");
		map.put(244, "201306/abf3880e-b50f-4faa-af1e-037d4f4a3349");
		map.put(243, "201306/5ce6d699-09f1-44b8-a25e-cbea01a3dce4");
		map.put(242, "201306/38d2409c-d086-4669-8173-e57c8f1410b1");
		map.put(241, "201306/6dbd7172-d7e3-42c4-a268-bcfad899294a");
		map.put(240, "201306/3d47cb8b-f024-4f25-b11d-6c04dfc8c8e5");
		map.put(239, "201306/9579aefa-c7e7-4693-be90-9b18714bda4f");
		map.put(238, "201306/ce19c474-69fb-4338-ae29-f1766b1431ef");
		map.put(237, "201306/2238d713-e6d5-431e-92ec-bab1ca6ff85e");
		map.put(235, "201306/fb7f6097-43cd-466e-9704-084ab9dcbfd4");
		map.put(234, "201306/0ef45c29-a546-4b97-a83c-fe9fcb5efa73");
		map.put(233, "201306/2954feca-4246-4680-800b-e8b31ad26013");
		map.put(251, "201306/8e5c4356-9bf1-4907-bf21-c73bd3d55dd2");
		map.put(252, "201306/5f1726f7-d58d-49d0-9534-683ae58b7763");
		map.put(253, "201306/5b03b359-e049-4271-97ed-ac89253d820b");
		map.put(254, "201306/0b20fa52-ff84-4a18-8079-76b08f434a17");
		map.put(255, "201306/3ce7ea54-053c-4659-9688-c8c4c6450ce7");
		map.put(256, "201306/ddf5632d-a0cd-4be2-a211-dcc95dd91ab9");
		map.put(257, "201306/678900fd-0753-4860-9ff2-b102a2060e24");
		map.put(258, "201306/93f63742-fc33-4358-8174-be2a72e9a6ad");
		map.put(259, "201306/3e654306-2dad-475f-903f-54cde682e026");
		map.put(260, "201306/f18ea953-ba7f-4a0e-b0ab-587e39a22ccc");
		map.put(261, "201306/de95ddf9-4d2a-47af-b8f2-9a8660105445");
		map.put(262, "201306/01b5ad63-08c4-4a62-a96d-0f0b114b9e24");
		map.put(263, "201306/682f8ec0-0c58-408f-bc2b-ab0871078b94");
		map.put(264, "201306/28e9f43b-6330-489d-ae4c-b9fe6ae9d043");
		map.put(265, "201306/eb57f0d6-5195-49f5-8184-3cd66cf84f79");
		map.put(266, "201306/34e4f79d-2b1b-4e0b-960e-2b7a0fe4c66e");
		map.put(267, "201306/f24ff2b3-a4ce-4c59-a5b6-44c81260a33e");
		map.put(268, "201306/94dc7ee8-4158-46ae-a4c3-d93a94bf9900");
		map.put(269, "201306/0faac7e4-9664-49e1-87ca-c8dd1b54fb42");
		map.put(270, "201306/855e7795-1be5-4ca7-8712-378701b0089c");
		map.put(271, "201306/656f6f3c-01bd-4c08-9e96-390535465db3");
		map.put(272, "201306/ef4435a0-e22b-429a-ad3d-ab2a2e2b9c32");
		map.put(273, "201306/1b62c6f1-4dcf-408e-97d4-095121a33cc9");


	}

	static String packagePath = "E:\\android_su_app\\pdouADNew3.5UTF";// 程序的path
	static String packagePathAPK = "C:\\Users\\suwg\\Desktop/";// 输出的地址 桌面
	static String names = "tantantu";// apk name
	static String namesend = "V3.5";// apk name



	static int startNumber = 276;// 渠道起始号

	public static void main(String[] args) {

		for (int i = 1; i <= map.size()+3; i++) {
			System.out.println("开始" + i);
			int index = startNumber + i;
			changeChannelFile(index);
			antRelease();
			boolean is = renameTo(index);
			if (is) {
				System.out.println("完成." + i + "个apk打包");

				System.err.println("===========================" + i);
				System.err.println("===========================" + i);
				System.err.println("===========================" + i);
				System.err.println("===========================" + i);
			} else {
				System.err.println("没有完成." + i + "个apk打包");
				break;
			}

		}

		System.err.println("全部完成");

	}

	private static boolean renameTo(int k) {
		// TODO Auto-generated method stub
		File fileapk = new File(packagePath + "/bin/pdouADNew-release.apk");

		File fileapknew = null;
		if (map.containsKey( k)) {

			fileapknew = new File(packagePathAPK + "getapks/" + map.get(k) +".apk");
		} else {
			fileapknew = new File(packagePathAPK + "getapks/" + names + "_"
					+ namesend + "_" + k + ".apk");
		}

		if (fileapknew.exists()) {
			fileapknew.deleteOnExit();
			try {
				fileapknew.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fileapk.renameTo(fileapknew);

	}

	private static void antRelease() {
		// TODO Auto-generated method stub

		System.out.println("开始编译apk");
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"E:/apache-ant-1.8.2/bin/ant.bat -buildfile " + packagePath
							+ "/build.xml release");

			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("编译" + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void changeChannelFile(int k) {

		File file = new File(packagePath
				+ "/src/cc/pdou/deskad/channel/Channel.java");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write("package cc.pdou.deskad.channel;\n\t");
			writer.write("public class Channel {\n\t");
			writer.write(("public static  String CANNEL_CODE=\"" + k + "\";" + "\n\t"));
			writer.write("}\n\t");
			writer.flush();
			writer.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// =================================================================================
		System.out.println("文件修改完成" + k);

	}

}
