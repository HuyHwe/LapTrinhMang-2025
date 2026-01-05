/*
[Mã câu hỏi (qCode): NaW6gVSa].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
      •	Tên đầy đủ của lớp: TCP.Laptop
      •	Các thuộc tính: id int, code String, name String, quantity int
      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
      •	Trường dữ liệu: private static final long serialVersionUID = 20150711L;
b)	Tương tác với server theo kịch bản
1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
        2)	Nhận một đối tượng là thể hiện của lớp Laptop từ server
3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
4)	Đóng socket và kết thúc chương trình.
        */

import TCP.Laptop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPObjectSanPham {
    static String HOST = "203.162.10.109";
    static int PORT = 2209;
    static String initMsg = "B22DCCN385;NaW6gVSa";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket client = new Socket(HOST, PORT);
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

        oos.writeObject(initMsg);
        oos.flush();
        System.out.println("Sent: " + initMsg);

        Laptop laptop = (Laptop) ois.readObject();

        System.out.println("get: " + laptop.toString());
        String[] name = laptop.name.split(" ");
        StringBuilder newName = new StringBuilder();
        newName.append(name[name.length-1]).append(" ");
        for (int i = 1; i <= name.length-2; i++) {
            newName.append(name[i]).append(" ");
        }
        newName.append(name[0]);
        laptop.name = newName.toString().trim();

        String quantity = Integer.toString(laptop.quantity);
        StringBuilder newQuant = new StringBuilder();
        for (int i = quantity.length()-1; i >= 0; i--) {
            newQuant.append(quantity.charAt(i));
        }

        laptop.quantity = Integer.parseInt(newQuant.toString());

        oos.writeObject(laptop);
        oos.flush();

        System.out.println("sent: " + laptop.toString());








    }
}
