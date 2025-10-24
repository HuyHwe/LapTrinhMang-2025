import UDP.Product;

import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 [Mã câu hỏi (qCode): GRROo81C].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:

 a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5B7".

 b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với:
 - requestId là chuỗi ngẫu nhiên duy nhất.
 - num là một số nguyên lớn.

 c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng "requestId;sumDigits".

 d. Đóng socket và kết thúc chương trình.
 **/

public class UDPGRROo81C {
    static String initMsg = ";B22DCCN385;GRROo81C";
    static String HOST = "203.162.10.109";
    static int PORT = 2207;
    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendByte = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendByte, sendByte.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Sent: " + sendPacket);



            byte[] buffer = new byte[1024];
            DatagramPacket revPacket = new DatagramPacket(buffer, buffer.length);
            client.receive(revPacket);
            String data = new String(revPacket.getData(), 0, revPacket.getLength());
            String code = data.split(";")[0];
            String num = data.split(";")[1];
            int sum = 0;
            for (int i = 0; i < num.length(); i++) {
                sum += Integer.parseInt(String.valueOf(num.charAt(i)));

            }

            StringBuilder res = new StringBuilder(code);
            res.append(";").append(Integer.toString(sum));

            sendByte = res.toString().getBytes();
            sendPacket = new DatagramPacket(sendByte, sendByte.length, address, PORT);
            client.send(sendPacket);
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
