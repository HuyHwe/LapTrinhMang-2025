/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 [Mã câu hỏi (qCode): NyEKIQkI].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
 a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;5B35BCC1”
 b.	Nhận thông điệp từ server theo định dạng “requestId;data”
 -	requestId là một chuỗi ngẫu nhiên duy nhất
 -	data là chuỗi dữ liệu cần xử lý
 c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc
 i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
 ii.	Các ký tự còn lại của chuỗi là in thường
 Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng “requestId;data”
 d.	Đóng socket và kết thúc chương trình
 */
public class NyEKIQkIUDP {

    static String initMsg = ";B22DCCN385;NyEKIQkI";
    static int PORT = 2208;
    static String HOST = "203.162.10.109";

    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address =  InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Gửi: " + initMsg);

            byte[] receivedData = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            client.receive(receivedPacket);
            String receivedMsg = new String(receivedData, 0, receivedPacket.getLength());
            System.out.println("Nhận: " + receivedMsg);
            String code = receivedMsg.split(";")[0];
            String words = receivedMsg.split(";")[1];
            String[] arr = words.split(" ");
            StringBuilder finalString = new StringBuilder(code);
            finalString.append(";");
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].toLowerCase();
                StringBuilder sb = new StringBuilder(arr[i]);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                arr[i] = sb.toString();
                finalString.append(arr[i]).append(" ");

            }

            System.out.println("Gui: " + finalString);
            sendData = finalString.toString().getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);

            client.close();




        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
