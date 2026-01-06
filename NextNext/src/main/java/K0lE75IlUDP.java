import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;


import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
        import java.util.ArrayList;
import java.util.Comparator;

/** [Mã câu hỏi (qCode): K0lE75Il].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
 a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode".
 Ví dụ: ";B15DCCN010;D3F9A7B8"
 b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a;b", với:
 •	requestId là chuỗi ngẫu nhiên duy nhất.
 •	a và b là chuỗi thể hiện hai số nguyên lớn (hơn hoặc bằng 10 chữ số).
 Ví dụ: "X1Y2Z3;9876543210;123456789"
 c. Tính tổng và hiệu của hai số a và b, gửi thông điệp lên server theo định dạng "requestId;sum,difference".Ví dụ:
 Nếu nhận được "X1Y2Z3;9876543210,123456789", tổng là 9999999999 và hiệu là 9753086421. Kết quả gửi lại sẽ là "X1Y2Z3;9999999999,9753086421".
 d. Đóng socket và kết thúc chương trình
 **/

public class K0lE75IlUDP {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2207;
    public static String initMsg = ";B22DCCN385;K0lE75Il";


    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendByte = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendByte, sendByte.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send: " + initMsg);

            byte[] receivedByte = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedByte, receivedByte.length);
            client.receive(receivedPacket);
            String receivedMsg = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedMsg);


            String code = receivedMsg.split(";")[0];
            BigInteger num1 = new BigInteger(receivedMsg.split(";")[1]);
            BigInteger num2 = new BigInteger(receivedMsg.split(";")[2]);

            BigInteger sum = num1.add(num2);
            BigInteger difference = num1.subtract(num2).abs();
            StringBuilder sb = new StringBuilder(code);
            sb.append(";");
            sb.append(sum.toString());
            sb.append(",");
            sb.append(difference.toString());

            sendByte = sb.toString().getBytes();
            sendPacket = new DatagramPacket(sendByte, sendByte.length, address, PORT);
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
