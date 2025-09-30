/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package next;

import TCP.Product;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 [Mã câu hỏi (qCode): nOJffoIm].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
 a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
 b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50”
 -	requestId là chuỗi ngẫu nhiên duy nhất
 -	a1 -> a50 là 50 số nguyên ngẫu nhiên
 c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
 d.	Đóng socket và kết thúc chương trình
 */
public class nOJffoImUDPDataType {
    static String initMsg = ";B22DCCN385;nOJffoIm";
    static int PORT = 2207;
    static String HOST = "203.162.10.109";

    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            byte[] sendData = initMsg.getBytes();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
            clientSocket.send(sendPacket);
            System.out.println("Gửi: " + initMsg);
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String receivedMessage = new String(receivePacket.getData());
            System.out.println("Nhận: " + receivedMessage);




        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
