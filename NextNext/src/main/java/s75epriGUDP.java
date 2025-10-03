import UDP.Product;

import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 * [Mã câu hỏi (qCode): s75epriG].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
 * a.	Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
 * b.	Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
 *
 * Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
 * a.	Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
 * •	Tên đầy đủ của lớp: UDP.Product
 * •	Các thuộc tính: id String, code String, name String, quantity int
 * •	Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
 * •	Trường dữ liệu: private static final long serialVersionUID = 20161107;
 * b.	Giao tiếp với server theo kịch bản
 * •       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
 *
 * •	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
 * •	Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc:
 * 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
 * •	Đóng socket và kết thúc chương trình.**/

public class s75epriGUDP {
    static String initMsg = ";B22DCCN385;s75epriG";
    static String HOST = "203.162.10.109";
    static int PORT = 2209;
    public static void main(String[] args) {
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
            byte[] receivedData = Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());

            String code = new String(receivedData, 0, 8);
            System.out.println("Get code: " + code);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData, 8, receivedData.length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Product product = (Product) ois.readObject();
            System.out.println("Doi tuong product: " + product.toString());

            String[] nameArr = product.name.split(" ");
            StringBuilder name = new StringBuilder();
            name.append(nameArr[nameArr.length-1]).append(" ");

            for (int i = 1; i < nameArr.length - 1; i++) {
                name.append(nameArr[i]).append(" ");
            }

            name.append(nameArr[0]);
            product.name = name.toString().trim();

            StringBuilder quant = new StringBuilder(Integer.toString(product.quantity));
            product.quantity = Integer.parseInt(quant.reverse().toString());

            System.out.println("Doi tuong product fixed: " + product.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(product);
            byte[] byteProduct = baos.toByteArray();
            byte[] responseData = new byte[8 + byteProduct.length];
            System.arraycopy(code.getBytes(), 0, responseData, 0, 8);
            System.arraycopy(byteProduct, 0, responseData, 8, byteProduct.length);

            sendPacket = new DatagramPacket(responseData, responseData.length, address, PORT);
            client.send(sendPacket);

            client.close();




        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
