import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HandlePy {

    public  void execPy(HashMap accountmap,HashMap colormap,int account,int color0) {
        HashMap hashMap=new HashMap();
        String result = "";
        Process process;
        String[] args=null;
        String[] res=null;
        {
            try {
                process = Runtime.getRuntime().exec("python /home/pp/newFile"+String.valueOf(account)+".py");
                InputStream in = process.getInputStream();
                byte[] buffer = new byte[1024];
                int size = in.read(buffer);
                result = new String(buffer, 0, size);
                args=result.split("\n");
                for (int i=0;i<4;i++){
                    res=args[i].split("=");
                    hashMap.put(res[0],res[1]);
                }
                hashMap.put("color",color0);

                colormap.put(color0,hashMap);

                System.out.println("map--------->"+hashMap);
                accountmap.put(account,hashMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     // int  color = (int) hashMap.get("color");

        HashMap hashMap1 = (HashMap) accountmap.get(account);


        System.out.println("hashmap----------------------------------------------------------->" + hashMap);
        System.out.println("hashmap1----------------------------------------------------------->" + hashMap1);
        int color = (int) hashMap1.get("color");
        System.out.println("color   " +color);

        System.out.println("execPy success");
    }


    public void createPy(String string,int account)  {
      File f=new File("/home/pp/newFile"+String.valueOf(account)+".py");
        if(f.exists()){
            f.delete();
        }


        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f,true);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
        byte[] bbuf = string.getBytes();
        try {
            fos.write(bbuf , 0 , bbuf.length);
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        System.out.println("create success");

    }



    public static void main(String[] args) {
        HandlePy handle=new HandlePy();
        String string="asdasd";
        int account=111;
        handle.createPy(string,account);

//        HashMap accountmap=new HashMap();
//        HashMap colormap=new HashMap();
//        handle.execPy(accountmap,colormap,account,1);
//        System.out.println(accountmap.get(account));

    }


}