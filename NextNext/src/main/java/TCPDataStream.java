import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 [Mã câu hỏi (qCode): aO1rj1yh].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu sinh viên xây dựng chương trình client để tương tác với server, sử dụng các luồng data (DataInputStream và DataOutputStream) để trao đổi thông tin theo thứ tự sau:
 a. Gửi mã sinh viên và mã câu hỏi: Chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7".
 b. Nhận một số nguyên hệ thập phân từ server. Ví dụ:: 15226.
 c. Chuyển đổi số nguyên nhận được sang hệ nhị phân và thập lục phân, ghép thành chuỗi và gửi lên server. Ví dụ: 15226 sẽ thành "11101101111010;3B7A"
 d. Đóng kết nối: Kết thúc chương trình sau khi gửi kết quả chuyển đổi.
**/
public class TCPDataStream {
    static String initMsg = "B22DCCN385;aO1rj1yh";
    static int PORT = 2207;
    static String HOST = "203.162.10.109";
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(HOST, PORT);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(initMsg);
        out.flush();
        System.out.println("Sent: " + initMsg);

        int num = in.readInt();
        String hex = Integer.toHexString(num).toUpperCase();
        String bin = Integer.toBinaryString(num);
        StringBuilder sb = new StringBuilder(bin);
        sb.append(";").append(hex);
        out.writeUTF(sb.toString());
        out.flush();
        socket.close();
    }
}
