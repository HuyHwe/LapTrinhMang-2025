import UDP.Book;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

/** [Mã câu hỏi (qCode): dU9nZtxU].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

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
 Chuẩn hóa author theo định dạng "Họ, Tên".
 Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
 Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
 d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.

 Đóng socket và kết thúc chương trình.
**/

public class UDPdU9nZtxU {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2209;
    public static String initMsg = ";B22DCCN385;dU9nZtxU";

    static String chuanHoaTitle(String title) {
        StringBuilder res = new StringBuilder();
        for (String s : title.split(" ")) {
            StringBuilder sb = new StringBuilder(s.toLowerCase());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            res.append(sb).append(" ");
        }
        return res.toString().trim();
    }

    static String chuanHoaTen(String ten) {
        String[] arr = ten.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0].toUpperCase());
        sb.append(", ");
        for (int i = 1; i < arr.length; i++) {
            StringBuilder s = new StringBuilder(arr[i].toLowerCase());
            s.setCharAt(0, Character.toUpperCase(s.charAt(0)));
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }

    static String chuanHoaIsbn(String isbn) {
        StringBuilder sb = new StringBuilder();
        sb.append(isbn, 0, 3).append("-");
        sb.append(isbn, 3, 4).append("-");
        sb.append(isbn, 4, 6).append("-");
        sb.append(isbn, 6, 12).append("-");
        sb.append(isbn, 12, 13);
        return sb.toString();

    }

    static String chuanHoaDate(String date) {
        String[] arr = date.split("-");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[1]).append("/");
        sb.append(arr[0]);
        return sb.toString();
    }



    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send: " + initMsg);

            byte[] receivedByte = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receivedByte, receivedByte.length);
            client.receive(receivedPacket);

            byte[] receivedData = Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());
            String code = new String(receivedData, 0, 8);
            System.out.println("Code: " + code);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData, 8, receivedData.length-8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Book book = (Book) ois.readObject();

            book.isbn = chuanHoaIsbn(book.isbn);
            book.publishDate = chuanHoaDate(book.publishDate);
            book.author = chuanHoaTen(book.author);
            book.title = chuanHoaTitle(book.title);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(book);
            byte[] byteBook = baos.toByteArray();
            byte[] result = new byte[8 + byteBook.length];
            System.arraycopy(code.getBytes(), 0, result, 0, 8);
            System.arraycopy(byteBook, 0, result, 8, byteBook.length);

            sendPacket = new DatagramPacket(result, result.length, address, PORT);
            client.send(sendPacket);

            client.close();



            System.out.println(book);



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
