package com.suyi.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class Cmd {

    public static int runCmd(String cmd) {

        Su.log("cmd:"+cmd);
        BufferedReader br = null, bre = null;
        Process p=null;
        try {
            p = Runtime.getRuntime().exec(cmd);
//            p.waitFor();
//            p.destroy();
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("utf-8")));
            bre = new BufferedReader(new InputStreamReader(p.getErrorStream(), Charset.forName("utf-8")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();


            {
//                Su.log("read pro");
                while ((line = br.readLine()) != null) {
                    sb1.append(line + "\n");
                }
                Su.log(sb1.toString());

            }

            {
//                Su.log("read err");
                while ((line = bre.readLine()) != null) {
                    sb.append(line + "\n");
                }
                Su.logE(sb.toString());
            }

            if (sb1.length() > 0) {
                return 0;
            }
            if (sb.length() > 0) {
                return 1;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bre != null) {
                try {
                    bre.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(p!=null){
                p.destroy();
            }
        }
        return 0;
    }

	public static List runCmdForList(String cmd) {
        Su.log("cmd:"+cmd);
        BufferedReader br = null, bre = null;
        Process p=null;
        
        List<String> list=new ArrayList<String>();
        try {
            p = Runtime.getRuntime().exec(cmd);
//            p.waitFor();
//            p.destroy();
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("utf-8")));
            bre = new BufferedReader(new InputStreamReader(p.getErrorStream(), Charset.forName("utf-8")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();


            {
//                Su.log("read pro");
                while ((line = br.readLine()) != null) {
                    sb1.append(line + "\n");
                    list.add(line);
                }
//                Su.log(sb1.toString());

            }

            {
//                Su.log("read err");
                while ((line = bre.readLine()) != null) {
                    sb.append(line + "\n");
                }
//                Su.logE(sb.toString());
            }

            if (sb1.length() > 0) {
                return list;
            }
            if (sb.length() > 0) {
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bre != null) {
                try {
                    bre.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(p!=null){
                p.destroy();
            }
        }
    
		// TODO Auto-generated method stub
		return null;
	}


//    {
//        {
//            Runtime runtime = Runtime.getRuntime();
//            Process child = null;
//            try {
//                child = runtime.exec(cmdcope);
//                InputStream in = child.getInputStream();
//                String output = null;
//                int isSuccessful = 0;
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(in));
//                output = bufferedReader.readLine();
//                while (output != null) {
//                    System.out.println(output);
//                    output = bufferedReader.readLine();
//                }
//
//                bufferedReader.close();
//                in.close();
//
//
//                in = child.getErrorStream();
//                bufferedReader = new BufferedReader(new InputStreamReader(in));
//                output = bufferedReader.readLine();
//                while (output != null) {
//                    System.out.println(output);
//                    output = bufferedReader.readLine();
//                }
//
//                try {
//                    isSuccessful = child.waitFor();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            } finally {
//                if(child!=null)
//                    child.destroy();
//            }
//            System.out.println("------------>ok");
//        }
//    }
}
