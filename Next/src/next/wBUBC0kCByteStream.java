/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package next;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
[Mã câu hỏi (qCode): wBUBC0kC].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
Ex: 2|5|9|11
c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
Ex: 27
d.	Đóng kết nối và kết thúc
 */
public class wBUBC0kCByteStream {
    static String initMsg = "B22DCCN385;wBUBC0kC";
    static String HOST = "203.162.10.109";
    static int PORT = 2206;
    public static void main(String args[]) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            
            dos.writeUTF(initMsg);
            dos.flush();
            System.out.println("Sent: " + initMsg);
            
            byte[] buffer = new byte[2048];
            int byteRead = dis.read(buffer);
            if (byteRead != -1) {
                String data = new String(buffer, 0, byteRead);
                System.out.println(data);
                long sum = 0;
                for (String s : data.split("\\|")) {
                    sum += Integer.parseInt(s);
                }
                System.out.println(sum);
                dos.writeUTF(Long.toString(sum));
                dos.flush();
                
                
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(wBUBC0kCByteStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    
}
