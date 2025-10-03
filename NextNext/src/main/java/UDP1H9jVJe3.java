/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import UDP.Book;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 [Mã câu hỏi (qCode): 1H9jVJe3].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

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
 */
public class UDP1H9jVJe3 {

    static String initMsg = ";B22DCCN385;1H9jVJe3";
    static int PORT = 2209;
    static String HOST = "203.162.10.109";

    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address =  InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Gửi: " + initMsg);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            client.receive(receivedPacket);
            byte[] receivedData = java.util.Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());

            // Tách requestId (8 byte đầu) và đối tượng Book
            String requestId = new String(receivedData, 0, 8);
            System.out.println("\nNhận được RequestId: " + requestId);

            Book book;
            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData, 8, receivedData.length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            book = (Book) ois.readObject();
            System.out.println("Nhận được đối tượng Book: " + book.toString());

            // Title
            StringBuilder titleFinal = new StringBuilder();
            for (String s : book.title.toLowerCase().split(" ")) {
                StringBuilder sb = new StringBuilder(s);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                titleFinal.append(sb.toString()).append(" ");
            }
            book.title = titleFinal.toString().trim();

            // author
            StringBuilder authorFinal = new StringBuilder();
            for (String s : book.author.split(" ")) {
                if (authorFinal.toString().isEmpty()) {
                    authorFinal.append(s).append(", ");
                } else authorFinal.append(s).append(" ");
            }
            authorFinal.deleteCharAt(authorFinal.length()-1);
            StringBuilder authorfinalnormal = new StringBuilder();
            int cnt = 0;
            for (String s : authorFinal.toString().toLowerCase().split(" ")) {
                if (authorfinalnormal.toString().isEmpty()){
                    authorfinalnormal.append(s.toUpperCase()).append(" ");
                    continue;
                }
                StringBuilder sb = new StringBuilder(s);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                authorfinalnormal.append(sb.toString()).append(" ");
            }
            book.author = authorfinalnormal.toString().trim();

            // isbn
            String digits = book.isbn;
            String finalISBN = digits.substring(0, 3) + "-" +
                    digits.substring(3, 4) + "-" +
                    digits.substring(4, 6) + "-" +
                    digits.substring(6, 12) + "-" +
                    digits.substring(12, 13);
            book.isbn = finalISBN;

            // publishDate
//            String[] pDate = book.publishDate.split("-");
//            StringBuilder pDateFinal = new StringBuilder();
//            pDateFinal.append(pDate[1]).append("/").append(pDate[0]);
//            book.publishDate = pDateFinal.toString();

            System.out.println("Sau chuan hoa: " + book.toString());

            byte[] bookByte;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(book);
            bookByte = baos.toByteArray();

            byte[] responseData = new byte[8 + bookByte.length];
            System.arraycopy(requestId.getBytes(), 0, responseData, 0, 8);
            System.arraycopy(bookByte, 0, responseData, 8, bookByte.length);

            sendPacket = new DatagramPacket(responseData, 0, responseData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Gửi lại đối tượng Book đã chuẩn hóa với RequestId: " + requestId);
            client.close();





        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
