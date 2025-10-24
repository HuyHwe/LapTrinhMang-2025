import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 [Mã câu hỏi (qCode): 0RdvBfEA].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu sinh viên xây dựng chương trình client để tương tác với server, sử dụng các luồng data (DataInputStream và DataOutputStream) để trao đổi thông tin theo thứ tự sau:
 a. Gửi mã sinh viên và mã câu hỏi: Chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7".
 b. Nhận một số nguyên hệ thập phân từ server. Ví dụ: 45
 c. Chuyển đổi số nguyên nhận được sang hệ nhị phân và gửi chuỗi kết quả này lại cho server. Ví dụ: 45 sẽ thành chuỗi "101101"
 d. Đóng kết nối và kết thúc chương trình.
 */
public class TCP0RdvBfEA {
    static String initialMsg = "B22DCCN385;0RdvBfEA";
    static String HOST = "203.162.10.109";
    static int PORT = 2207;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(initialMsg);
            dos.flush();
            System.out.println("Sent: " + initialMsg);
            int num = dis.readInt();
            String res = Integer.toBinaryString(num);
            System.out.println("Sent: " + res);
            dos.writeUTF(res);
            dos.flush();
//            byte[] buffer = new byte[1024];
//            int byteRead = dis.read(buffer);
//            if (byteRead != -1) {
//                String data = new String(buffer, 0, byteRead);
//                int num = Integer.parseInt(data);
//                String res = Integer.toBinaryString(num);
//                System.out.println("Sent: " + res);
//                dos.writeUTF(res);
//                dos.flush();
//
//            }

            socket.close();




        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
