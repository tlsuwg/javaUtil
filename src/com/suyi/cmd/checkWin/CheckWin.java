package com.suyi.cmd.checkWin;

import java.util.List;

import com.suyi.cmd.Cmd;
import com.suyi.cmd.StringUtils;
import com.suyi.cmd.Su;

public class CheckWin {
	
	String killeds[] = new String[] { "PresentationFontCache___","MsMpEng", "svchost" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final CheckWin mCheckWin=new CheckWin();
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					mCheckWin.findKill();
					try {
						Thread.sleep(1000*10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
	
	private void findKill() {
		// TODO Auto-generated method stub
		List<String> list = Cmd.runCmdForList("tasklist /FI \"CPUTIME gt 00:00:10\"");
		if (list != null)
			for (String info : list) {
				for (String killed : killeds) {
					if (info.contains(killed)) {
						Su.log("发现" + info);
						String id = findID(info);
						if (StringUtils.isNull(id)) {
							Su.log("没有找到这个ID");
						} else {
							Su.log("结束" + id);
							List<String> listend = Cmd.runCmdForList("tskill  " + id);
							StringUtils.show(listend);
						}
					}
				}
			}
	}

	private static String findID(String info) {
		// TODO Auto-generated method stub
		String infos[] = info.split(" ");
		String id = null;
		if (infos != null && infos.length > 6) {
			int icc = 0;
			for (String cc : infos) {
				if (!StringUtils.isNull(cc)) {
					icc++;
					if (icc == 2) {
						id = cc;
					}
//					Su.log(cc);
				}
			}
		}
		return id;
	}

}
