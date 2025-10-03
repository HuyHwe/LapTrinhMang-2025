import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;

/** [Mã câu hỏi (qCode): nOJffoIm].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,aN”
        -	requestId là chuỗi ngẫu nhiên duy nhất
-	a1 -> aN là N số nguyên ngẫu nhiên
c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> aN và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
d.	Đóng socket và kết thúc chương trình
**/

public class nOJffoImUDP {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2207;
    public static String initMsg = ";B22DCCN385;nOJffoIm";


    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send: " + initMsg);

            byte[] receivedData = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            client.receive(receivedPacket);
            String receivedMsg = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedMsg);

            String code = receivedMsg.split(";")[0];
            ArrayList<Integer> arr = new ArrayList<>();
            String left = receivedMsg.split(";")[1];
            for (String s : left.split(",")) {
                arr.add(Integer.parseInt(s));
            }
            arr.sort((a, b) -> a - b);
            int max = arr.getLast();
            int min = arr.getFirst();
            StringBuilder sb = new StringBuilder(code);
            sb.append(";");
            sb.append(Integer.toString(max)).append(",");
            sb.append(Integer.toString(min));

            sendData = sb.toString().getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send: " + sb.toString());
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
