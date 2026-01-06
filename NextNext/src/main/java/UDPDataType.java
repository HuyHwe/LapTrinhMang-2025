import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 [Mã câu hỏi (qCode): 4Q4HSHH9].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
 a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
 b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50”
 -	requestId là chuỗi ngẫu nhiên duy nhất
 -	a1 -> a50 là 50 số nguyên ngẫu nhiên
 c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
 d.	Đóng socket và kết thúc chương trình
 */

public class UDPDataType {
    static String initMsg = ";B22DCCN385;4Q4HSHH9";
    static int PORT = 2207;
    static String HOST = "203.162.10.109";
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        InetAddress address = InetAddress.getByName(HOST);
        DatagramPacket sendPacket = new DatagramPacket(initMsg.getBytes(), initMsg.getBytes().length, address, PORT);
        client.send(sendPacket);

        System.out.println("Sent: " + initMsg);

        byte[] buffer = new byte[2048];
        DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
        client.receive(receivedPacket);

        String receivedStr = new String(receivedPacket.getData(), 0, receivedPacket.getLength());

        String code = receivedStr.split(";")[0];
        String[] arr = receivedStr.split(";")[1].split(",");
        int max = -1;
        int min = 999999999;

        for (String s: arr) {
            int n = Integer.parseInt(s);
            if (n > max) max = n;
            if (n < min) min = n;
        }

        StringBuilder sb = new StringBuilder(code);
        sb.append(";").append(Integer.toString(max)).append(",").append(Integer.toString(min));
        sendPacket = new DatagramPacket(sb.toString().getBytes(), sb.toString().getBytes().length, address, PORT);
        client.send(sendPacket);
        System.out.println("Sent: " + sb.toString());
        client.close();
    }
}
