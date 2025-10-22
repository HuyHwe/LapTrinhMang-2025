import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

//[Mã câu hỏi (qCode): Gu6R3BaC].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
//Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
//b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
//Ex: 2|5|9|11
//c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
//Ex: 27
//d.	Đóng kết nối và kết thúc

public class TCPGu6R3BaC {
    static String initialMsg = "B22DCCN385;Gu6R3BaC";
    static String HOST = "203.162.10.109";
    static int PORT = 2206;

    public static void main(String[] args) {

        try{
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(initialMsg);
            dos.flush();
            System.out.println("Sent: " + initialMsg);

            byte[] buffer = new byte[2048];
            int byteRead = dis.read(buffer);
            int sum = 0;
            if (byteRead != -1) {
                String data = new String(buffer, 0, byteRead);
                System.out.println("Received: " + data);
                for (String s : data.split("\\|")) {
                    sum += Integer.parseInt(s);
                }
            }

            dos.writeUTF(Integer.toString(sum));
            dos.flush();
            System.out.println("Send: " + Integer.toString(sum));
            socket.close();


        } catch (IOException ex) {
            Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
