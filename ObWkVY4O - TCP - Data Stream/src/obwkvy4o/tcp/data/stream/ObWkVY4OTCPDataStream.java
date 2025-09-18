/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package obwkvy4o.tcp.data.stream;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.abs;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
[Mã câu hỏi (qCode): ObWkVY4O].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B10DCCN002;B4C5D6E7"
b. Nhận chuỗi chứa mảng số nguyên từ server, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: "1,3,2,5,4,7,6"
c. Tính số lần đổi chiều và tổng độ biến thiên trong dãy số.
- Đổi chiều: Khi dãy chuyển từ tăng sang giảm hoặc từ giảm sang tăng 
-   Độ biến thiên: Tổng giá trị tuyệt đối của các hiệu số liên tiếp
Gửi lần lượt lên server: số nguyên đại diện cho số lần đổi chiều, sau đó là số nguyên đại diện cho tổng độ biến thiên. Ví dụ: Với mảng "1,3,2,5,4,7,6", số lần đổi chiều: 5 lần, Tổng độ biến thiên 11 -> Gửi lần lượt số nguyên 5 và 11 lên server.
d. Đóng kết nối và kết thúc chương trình.
 */
public class ObWkVY4OTCPDataStream {
    private static String initialMsg = "B22DCCN385;ObWkVY4O";
    private static String HOST = "203.162.10.109";
    private static int PORT = 2207;
    public static void main(String[] args) {
        
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try{
            socket = new Socket(HOST, PORT);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos.writeUTF(initialMsg);
            dos.writeUTF("\n");
            dos.flush();
            System.out.println("Sent: " + initialMsg);
            if (true) {
                ArrayList<Integer> arr = new ArrayList<>();
                String data = dis.readUTF();
                for (String s: data.split(",")) {
                    arr.add(Integer.parseInt(s));
                    System.out.println(arr.getLast());
                }
                
                int dif = (arr.get(1) - arr.get(0));
                int cur = dif / abs(dif);
                dif = abs(dif);
                int cnt = 0;
                for (int i = 2; i < arr.size(); i++) {
                    if (arr.get(i) - arr.get(i-1) > 0 && cur < 0) {
                        cnt++;
                        cur = 1;
                    } else if (arr.get(i) - arr.get(i-1) < 0 && cur > 0) {
                        cnt++;
                        cur = -1;
                    }
                    
                    dif += abs(arr.get(i) - arr.get(i-1));
                
                }
                
                System.out.println("Dif: " + Integer.toString(dif) + " cnt: " + Integer.toString(cnt));
                dos.writeInt(cnt);
                dos.flush();

                dos.writeInt(dif);
                dos.flush();
                
                dis.close();
                dos.close();
                socket.close();
                
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ObWkVY4OTCPDataStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}
}