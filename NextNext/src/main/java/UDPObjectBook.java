import UDP.Book;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 [Mã câu hỏi (qCode): phaUIEJ9].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

 Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:

 Tên đầy đủ lớp: UDP.Book
 Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
 Hàm khởi tạo:
 public Book(String id, String title, String author, String isbn, String publishDate)
 Trường dữ liệu: private static final long serialVersionUID = 20251107L

 Thực hiện:

 a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"

 b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.

 c. Thực hiện:
 Chuẩn hóa title: viết hoa chữ cái đầu của mỗi từ.
 Chuẩn hóa author theo định dạng "HỌ, Tên".
 Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
 Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
 d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.

 Đóng socket và kết thúc chương trình.
**/
public class UDPObjectBook {
    static String HOST = "203.162.10.109";
    static int PORT = 2209;
    static String initMsg = ";B22DCCN385;phaUIEJ9";

    public static void main(String[] args) throws Exception{
        DatagramSocket client = new DatagramSocket();
        InetAddress address = InetAddress.getByName(HOST);
        DatagramPacket sendPacket = new DatagramPacket(initMsg.getBytes(), initMsg.getBytes().length, address, PORT);
        client.send(sendPacket);

        System.out.println("Sent: " + initMsg);

        byte[] buffer = new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
        client.receive(receivedPacket);

        byte[] data = Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());
        String requestId = new String(data, 0, 8);

        ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, data.length-8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Book book = (Book) ois.readObject();
        System.out.println("Get: " + book.toString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);

        byte[] sendByte = new byte[1024];
        System.arraycopy(requestId.getBytes(), 0, sendByte, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, sendByte, 8, baos.toByteArray().length);
        sendPacket = new DatagramPacket(sendByte, sendByte.length, address, PORT);
        client.send(sendPacket);


    }
}
