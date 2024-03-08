package socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket server = null;
        String cardID="";
        int money=0;
        int type=0;
        try {
            String inputString;
            server = new Socket("192.168.1.111", 8888);// 向本机4444端口发出客户请求


            CardUse cu=userInput();

//            BufferedReader sin = new BufferedReader(new InputStreamReader(
//                    System.in));
//            // 由系统标准输入设备构造BufferedReadder对象
            PrintWriter os = new PrintWriter(server.getOutputStream());
            // 由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader is = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
            // 由Socket对象得到输入流，并构造BufferedReader对象
//
//            inputString = sin.readLine();// 从标准输入读入一字符串（client端键盘输入的）

            while (cu != null) {// 如果该字符串为"quit",则停止循环
                os.println(cu);// 向Server端输出该字符串
                os.flush();// 刷新输出流，使Server端马上收到该字符串
                System.out.println("Client发送的消息为:卡号为" + cu.cardId+"的用户，选择操作类型"+cu.type+",操作的金额为"+cu.money+"元");
                // 在屏幕上显示读入的字符串
                System.out.println("Server发送的消息为:" + is.readLine());
                // 从Server读入一字符串，并打印到标准输出上
                System.out.println("是否终止本次连接？1是，0否");
                Scanner sc=new Scanner(System.in);
                int n=sc.nextInt();
                if(n==1)
                {
                    sc.close();
                    break;
                }
                cu=userInput();
            }
            os.close();// 关闭Socket输出流
            is.close();// 关闭Socket输入流
            server.close();// 关闭ServerSocket
            System.out.println("聊天结束!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static CardUse userInput()
    {
        String cardID="";
        int money=0;
        int type=0;
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入卡号:");
        cardID=sc.next();

        System.out.println("请选择校园卡操作类型：1充值，2消费（1/2)");
        type=sc.nextInt();
        if(type==1)
        {
            System.out.println("请输入充值金额:");
            money=sc.nextInt();
        }else{
            System.out.println("请输入消费金额：");
            money=sc.nextInt();
        }
        sc.close();
        CardUse cu=new CardUse(cardID,type,money);
        return cu;
    }
}// 客户端程序结束
