
//[Mã câu hỏi (qCode): 4Q4HSHH9].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50”
//        -	requestId là chuỗi ngẫu nhiên duy nhất
//-	a1 -> a50 là 50 số nguyên ngẫu nhiên
//c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
//d.	Đóng socket và kết thúc chương trình

import java.io.IOException;
import java.net.*;

public class UDP4Q4HSHH9 {
    static String initMsg = ";B22DCCN385;4Q4HSHH9";
    static int PORT = 2207;
    static String HOST = "203.162.10.109";

    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Sent: " + initMsg);

            byte[] receivedBuffer = new byte[2048];
            DatagramPacket receivedPacket = new DatagramPacket(receivedBuffer, receivedBuffer.length);
            client.receive(receivedPacket);
            String data = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            String code = data.split(";")[0];
            String[] arr = data.split(";")[1].split(",");

            int min = Integer.parseInt(arr[0]), max = Integer.parseInt(arr[0]);
            for (int i = 0; i < arr.length; i++) {
                int num = Integer.parseInt(arr[i]);
                if (num > max) max = num;
                if (num < min) min = num;
            }
            StringBuilder sb = new StringBuilder(code);
            sb.append(";").append(Integer.toString(max)).append(",").append(Integer.toString(min));

            sendData = sb.toString().getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Sent: " + sb.toString());
            client.close();







        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
