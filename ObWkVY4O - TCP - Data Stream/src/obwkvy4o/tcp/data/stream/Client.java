package obwkvy4o.tcp.data.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * [Mã câu hỏi (qCode): 8DF0AvHf]. Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
 * a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
 * Ví dụ: "B10DCCN002;B4C5D6E7"
 * b. Nhận chuỗi chứa mảng số nguyên từ server, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: "1,3,2,5,4,7,6"
 * c. Tính số lần đổi chiều và tổng độ biến thiên trong dãy số.
 * - Đổi chiều: Khi dãy chuyển từ tăng sang giảm hoặc từ giảm sang tăng
 * -   Độ biến thiên: Tổng giá trị tuyệt đối của các hiệu số liên tiếp
 * Gửi lần lượt lên server: số nguyên đại diện cho số lần đổi chiều, sau đó là số nguyên đại diện cho tổng độ biến thiên. Ví dụ: Với mảng "1,3,2,5,4,7,6", số lần đổi chiều: 5 lần, Tổng độ biến thiên 11 -> Gửi lần lượt số nguyên 5 và 11 lên server.
 * d. Đóng kết nối và kết thúc chương trình.
 *
 */

public class Client {
   private static String initialMsg = "B22DCCN385;ObWkVY4O";
   private static String HOST = "203.162.10.109";
   private static int PORT = 2207;

  public static void main(String[] args) {
    try{
      Socket socket = new Socket(HOST,PORT);
      String initMessage = initialMsg;

      DataInputStream dis = new DataInputStream(socket.getInputStream());
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      dos.writeUTF(initMessage);
      dos.flush();

      String data = dis.readUTF();
      int[] arr = Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray();
      System.out.println("Data from Server: "+data);

      int soLanDoiChieu=0;
      int doBienThien=0;
      boolean check;
      if(arr[0]<arr[1]) check = false;//tang
      else check = true; // tang
      doBienThien+=Math.abs(arr[0]-arr[1]);

      for(int i=1;i<arr.length-1;++i)
      {
        if(arr[i]<arr[i+1]){
          if(check == true) // doi chieu
          {
            soLanDoiChieu++;
            check = !check;
          }

        }
        else {
          if(check == false)
          {
            soLanDoiChieu++;
            check = !check;
          }
        }

        doBienThien+= Math.abs(arr[i] - arr[i+1]);
      }

      dos.writeInt(soLanDoiChieu);
      dos.flush();
      dos.writeInt(doBienThien);
      dos.flush();
      System.out.println("Đã gửi lên server. Số lần đổi chiều: "+soLanDoiChieu+" với độ biến thiên: "+doBienThien);

      socket.close();
      dis.close();
      dos.close();

    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



}