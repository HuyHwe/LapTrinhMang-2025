/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package next;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.sqrt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
[Mã câu hỏi (qCode): 4lVn2BLA].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B16DCCN999;C89DAB45"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
Ví dụ: "8,4,2,10,5,6,1,3"
c. Tính tổng của tất cả các số nguyên tố trong chuỗi và gửi kết quả lên server.
Ví dụ: Với dãy "8,4,2,10,5,6,1,3", các số nguyên tố là 2, 5, 3, tổng là 10. Gửi lên server chuỗi "10".
d. Đóng kết nối và kết thúc chương trình.
 */
public class DataStream4lVn2BLAD {
    static String initMsg = "B22DCCN385;4lVn2BLA";
    static int PORT = 2206;
    static String HOST = "203.162.10.109";
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(initMsg);
            dos.flush();
            System.out.println(initMsg);
//            String data = dis.readUTF();
//            System.out.println(data);
            
            byte[] buffer = new byte[100000];
            int byteRead = dis.read(buffer);
            if (byteRead != -1) {
                String data = new String(buffer, 0, byteRead);
                System.out.println(data);
                ArrayList<Integer> arr = new ArrayList<>();
                long sum = 0;
                for (String s : data.split(",")) {
                    int num = Integer.parseInt(s);
                    if (checkPrime(num)) sum += num;
                }
                dos.writeUTF(Long.toString(sum));
                dos.flush();
                System.out.println("Sent: " + Long.toString(sum));
                socket.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DataStream4lVn2BLAD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean checkPrime(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        for (int i = 2; i <= sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
