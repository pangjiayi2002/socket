package socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        Socket server = null;
        FileInputStream fis=null;
        ObjectInputStream in;
        ArrayList<Card> cardList=null;
        try {
            String inputString;
            server = new Socket("192.168.1.111", 8888);// 向本机4444端口发出客户请求
            System.out.println("请输入信息:");
            BufferedReader sin = new BufferedReader(new InputStreamReader(
                    System.in));
            // 由系统标准输入设备构造BufferedReadder对象
            PrintWriter os = new PrintWriter(server.getOutputStream());
            // 由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader is = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
            // 由Socket对象得到输入流，并构造BufferedReader对象
            try {
                fis = new FileInputStream(".\\card.dat");
            }catch (FileNotFoundException e){
                System.out.println("card文件还没创建，没有card数据表");
            }
            if(fis!=null){
                in=new ObjectInputStream(fis);
                cardList=(ArrayList<Card>) in.readObject();
                fis.close();
            }


            inputString = sin.readLine();// 从标准输入读入一字符串（client端键盘输入的）
            while (inputString != null && !inputString.trim().equals("quit")) {// 如果该字符串为"quit",则停止循环
                os.println(inputString);// 向Server端输出该字符串
                os.flush();// 刷新输出流，使Server端马上收到该字符串
                System.out.println("Client发送的消息为:" + inputString);
                // 在屏幕上显示读入的字符串
                System.out.println("Server发送的消息为:" + is.readLine());
                // 从Server读入一字符串，并打印到标准输出上
                inputString = sin.readLine(); // 从系统标准输入读入一字符串
            }
            os.close();// 关闭Socket输出流
            is.close();// 关闭Socket输入流
            server.close();// 关闭ServerSocket
            System.out.println("聊天结束!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}// 客户端程序结束
