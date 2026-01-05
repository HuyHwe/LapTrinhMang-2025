import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 [Mã câu hỏi (qCode): uKxheqxP].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
 a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;5B35BCC1"
 b.	Nhận thông điệp từ server theo định dạng "requestId;data"
 -	requestId là một chuỗi ngẫu nhiên duy nhất
 -	data là chuỗi dữ liệu cần xử lý
 c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc
 i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
 ii.	Các ký tự còn lại của chuỗi là in thường
 Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng "requestId;data"
 d.	Đóng socket và kết thúc chương trình
 **/
public class UDPString {
    static int PORT = 2208;
    static String HOST = "203.162.10.109";
    static String initMsg = ";B22DCCN385;uKxheqxP";

    public static void main(String[] args) throws Exception{
        DatagramSocket client = new DatagramSocket();
        InetAddress address = InetAddress.getByName(HOST);
        DatagramPacket sendPack = new DatagramPacket(initMsg.getBytes(), initMsg.getBytes().length, address, PORT);
        client.send(sendPack);
        System.out.println("Sent: " + initMsg);

        byte[] buffer = new byte[2048];
        DatagramPacket revPack = new DatagramPacket(buffer, buffer.length);
        client.receive(revPack);
        String str = new String(revPack.getData(), 0, revPack.getLength());

        String requestId = str.split(";")[0];
        String data = str.split(";")[1].toLowerCase();
        StringBuilder sb = new StringBuilder(requestId+";");
        for (String s : data.split(" ")) {
            StringBuilder word = new StringBuilder(s);
            word.setCharAt(0, Character.toUpperCase(word.charAt(0)));
            sb.append(word).append(" ");
        }

        sendPack = new DatagramPacket(sb.toString().trim().getBytes(), sb.toString().trim().getBytes().length, address, PORT);
        client.send(sendPack);
        System.out.println("Sent: " + sb.toString());




    }
}
