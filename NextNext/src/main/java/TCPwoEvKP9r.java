import TCP.Customer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 [Mã câu hỏi (qCode): woEvKP9r].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
 a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
 b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
 c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong

 Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
 a.	Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
 •	Tên đầy đủ của lớp: TCP.Customer
 •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
 •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
 •	Trường dữ liệu: private static final long serialVersionUID = 20170711L;
 b.	Tương tác với server theo kịch bản dưới đây:
 1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
 2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
 3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
 4) Đóng socket và kết thúc chương trình.
 */
public class TCPwoEvKP9r {
    static String initialMsg = "B22DCCN385;woEvKP9r";
    static String HOST = "203.162.10.109";
    static int PORT = 2209;

    public static String chuanHoaTen(String name) {
        String[] nameArr = name.split(" ");
        StringBuilder res = new StringBuilder(nameArr[nameArr.length-1].toUpperCase());
        res.append(", ");
        for (int i = 0; i < nameArr.length-1; i++) {
            StringBuilder sb = new StringBuilder(nameArr[i].toLowerCase());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            res.append(sb.toString()).append(" ");
        }

        return res.toString();

    }

    public static String chuanHoaDOB(String dob) {
        String[] arr = dob.split("-");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[1]).append("/");
        sb.append(arr[0]).append("/");
        sb.append(arr[2]);
        return  sb.toString();

    }

    public static String chuanHoaUsername(String name) {
        StringBuilder sb = new StringBuilder();
        String[] arr = name.split(" ");
        for (int i = 0; i < arr.length-1; i++) {
            sb.append(arr[i].toLowerCase().charAt(0));
        }
        sb.append(arr[arr.length-1].toLowerCase());
        return  sb.toString();
    }



    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
//            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(initialMsg);
            oos.flush();
            System.out.println("Sent: " + initialMsg);

            Customer cus = (Customer) ois.readObject();
            cus.userName = chuanHoaUsername(cus.name);
            cus.name = chuanHoaTen(cus.name);
            cus.dayOfBirth = chuanHoaDOB(cus.dayOfBirth);

            oos.writeObject(cus);
            oos.flush();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
