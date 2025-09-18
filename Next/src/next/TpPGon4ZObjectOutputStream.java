/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package next;

import TCP.Product;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
[Mã câu hỏi (qCode): TpPGon4Z].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:

Biết lớp TCP.Product gồm các thuộc tính (id int, name String, price double, int discount) và private static final long serialVersionUID = 20231107;

a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1E08CA31"

b. Nhận một đối tượng là thể hiện của lớp TCP.Product từ server.

c. Tính toán giá trị giảm giá theo price theo nguyên tắc: Giá trị giảm giá (discount) bằng tổng các chữ số trong phần nguyên của giá sản phẩm (price). Thực hiện gán giá trị cho thuộc tính discount và gửi lên đối tượng nhận được lên server.

d. Đóng kết nối và kết thúc chương trình.
 */
public class TpPGon4ZObjectOutputStream {
    static String initMsg = "B22DCCN385;TpPGon4Z";
    static int PORT = 2209;
    static String HOST = "203.162.10.109";
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
          
            oos.writeObject(initMsg);
            oos.flush();
            System.out.println("Sent: " + initMsg);
            
            Object obj = ois.readObject();
            if (!(obj instanceof Product)) {
                System.out.println("Nhan duoc cai nay: " + obj.getClass().getName());
            } else {
                Product prd = (Product) obj;
                int price = (int)prd.getPrice();
                int discount = 0;
                while (price != 0) {
                    discount += price % 10;
                    price /= 10;
                }
                prd.setDiscount(discount);
                oos.writeObject(prd);
                oos.flush();
                System.out.println(prd);
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(TpPGon4ZObjectOutputStream.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TpPGon4ZObjectOutputStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
