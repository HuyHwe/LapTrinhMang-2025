/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.nh8pzkis_datastream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**

[Mã câu hỏi (qCode): NH8PZkIs].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B10DCCN001;A1B2C3D4"
b. Nhận một số nguyên hệ thập phân từ server. Ví dụ: 255
c. Chuyển đổi số nguyên nhận được sang hai hệ cơ số 8 và 16. Gửi lần lượt chuỗi kết quả lên server. Ví dụ: Với số 255 hệ thập phân, kết quả gửi lên sẽ là hai chuỗi "377" và "FF".
d. Đóng kết nối và kết thúc chương trình.
 
*/
public class NH8PZkIs_DataStream {
    static String initMsg = "B22DCCN385;NH8PZkIs";
    static int PORT = 2207;
    static String HOST = "203.162.10.109";
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            
            dos.writeUTF(initMsg);
            dos.flush();
            System.out.println("Sent: " + initMsg);
            
            int number = dis.readInt();
            System.out.println("Received: " + Integer.toString(number));
            String base8 = Integer.toString(number, 8);
            String base16 = Integer.toString(number, 16);
            base16 = base16.toUpperCase();
            dos.writeUTF(base8);
            dos.writeUTF(base16);
            dos.flush();
            System.out.println("Sent: " + base8 + " " + base16);
            
            socket.close();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(NH8PZkIs_DataStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
