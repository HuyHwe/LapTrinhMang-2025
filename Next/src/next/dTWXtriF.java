/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package next;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 [Mã câu hỏi (qCode): dTWXtriF].
 Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
 a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN004;99D9F604”
 b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;z1,z2,...,z50” requestId là chuỗi ngẫu nhiên duy nhất
 z1 -> z50 là 50 số nguyên ngẫu nhiên
 c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai của thông điệp trong z1 -> z50 và gửi thông điệp lên server theo định dạng “requestId;secondMax,secondMin”
 d. Đóng socket và kết thúc chương trình
 */
public class dTWXtriF {
    static String initMsg = ";B22DCCN385;dTWXtriF";
    static String HOST = "203.162.10.109";
    static int PORT = 2207;
    static int BUFFER_SIZE = 2048;
    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);

            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Sent: " + initMsg);
            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            client.receive(receivePacket);
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Nhận: " + receivedMessage);

            int cnt = 0;
            String code = receivedMessage.split(";")[0];
            String numArr = receivedMessage.split(";")[1];
            ArrayList<Integer> arr = new ArrayList<>();
            for (String s : numArr.split(",")) {
                arr.add(Integer.parseInt(s));
                cnt++;
                if (cnt == 50) break;
            }

            arr.sort((a, b) -> {return a - b ;});
            StringBuilder sb = new StringBuilder();
            sb.append(code + ";");
            sb.append(Integer.toString(arr.get(arr.size()-2)) + ",");
            sb.append(Integer.toString(arr.get(1)));
            String result = sb.toString();
            sendData = result.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Gửi kết quả: " + result);
            client.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



            
    
}

