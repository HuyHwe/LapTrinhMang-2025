import UDP.Book;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
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

public class UDPObject {
    static String initMsg = ";B22DCCN385;phaUIEJ9";
    static int PORT = 2209;
    static String HOST = "203.162.10.109";

    static String chuanTit(String tit) {
        String[] arr = tit.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s: arr) {
            StringBuilder chuanStr = new StringBuilder(s.toLowerCase());
            chuanStr.setCharAt(0, Character.toUpperCase(chuanStr.charAt(0)));
            sb.append(chuanStr).append(" ");
        }

        return sb.toString().trim();
    }

    static String chuanTen(String name) {
        String ho = name.split(" ")[0];
        StringBuilder sb = new StringBuilder(ho.toUpperCase());
        sb.append(", ");
        String ten = name.substring(ho.length()+1);
        sb.append(chuanTit(ten));
        return sb.toString();
    }

    static String chuanIsbn(String isbn) {
        StringBuilder sb = new StringBuilder(isbn);
        sb.insert(3, "-");
        sb.insert(5, "-");
        sb.insert(8, "-");
        sb.insert(15, "-");
        return sb.toString();
    }

    static String chuanDate(String date) {
        String[] arr = date.split("-");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[1]).append("/").append(arr[0]);
        return sb.toString();
    }



    public static void main(String[] args) throws Exception{
        DatagramSocket client = new DatagramSocket();
        InetAddress address = InetAddress.getByName(HOST);
        DatagramPacket sendPacket = new DatagramPacket(initMsg.getBytes(), initMsg.getBytes().length, address, PORT);
        client.send(sendPacket);
        System.out.println("Sent: " + initMsg);

        byte[] buffer = new byte[2048];
        DatagramPacket revPacket = new DatagramPacket(buffer, buffer.length);
        client.receive(revPacket);
        byte[] revData = Arrays.copyOf(revPacket.getData(), revPacket.getLength());

        String requestId = new String(revData, 0, 8);
        System.out.println("reqId: " + requestId);

        Book book;
        ByteArrayInputStream bais = new ByteArrayInputStream(revData, 8, revData.length - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        book = (Book) ois.readObject();
        book.title = chuanTit(book.title);
        book.author = chuanTen(book.author);
        book.isbn = chuanIsbn(book.isbn);
        book.publishDate = chuanDate(book.publishDate);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
        buffer = new byte[2048];
        System.arraycopy(requestId.getBytes(), 0, buffer, 0, 8);
        System.arraycopy(baos.toByteArray(), 0, buffer, 8, baos.toByteArray().length);

        sendPacket = new DatagramPacket(buffer, buffer.length, address, PORT);
        client.send(sendPacket);
        System.out.println("book: " + book.toString());
        client.close();

    }

}
