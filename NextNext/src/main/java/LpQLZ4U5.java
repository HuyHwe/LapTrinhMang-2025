import UDP.Student;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Arrays;

/** [Mã câu hỏi (qCode): LpQLZ4U5].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
 Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
 •	Tên đầy đủ lớp: UDP.Student
 •	Các thuộc tính: id String,code String, name String, email String
 •	02 Hàm khởi tạo:
 o	public Student(String id, String code, String name, String email)
 o	public Student(String code)
 •	Trường dữ liệu: private static final long serialVersionUID = 20171107
 Thực hiện:
 •       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
 b.	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
 c.	Yêu cầu:
 -	Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
 -	Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
 -	Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
 d.	Đóng socket và kết thúc chương trình.
 **/

public class LpQLZ4U5 {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2209;
    public static String initMsg = ";B22DCCN385;LpQLZ4U5";

    static String chuanHoaTen(String ten){
        StringBuilder finalName = new StringBuilder();
        for (String s : ten.split(" ")) {
            StringBuilder sb = new StringBuilder(s.toLowerCase());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            finalName.append(sb.toString()).append(" ");
        }
        return finalName.toString().trim();
    }

    static String chuanHoaEmail(String name) {
        StringBuilder sb = new StringBuilder();
        String[] arr = name.split(" ");
        sb.append(arr[arr.length-1]);
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString().toLowerCase();
    }

    public static void main(String args[]) {
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

            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData, 8, receivedData.length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student student = (Student) ois.readObject();

            System.out.println("Get student: " + student.toString());

            student.name = chuanHoaTen(student.name);
            student.email = chuanHoaEmail(student.name);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            byte[] byteStudent = baos.toByteArray();
            byte[] responseData = new byte[8 + byteStudent.length];
            System.arraycopy(code.getBytes(), 0, responseData, 0, 8);
            System.arraycopy(byteStudent, 0, responseData, 8, byteStudent.length);

            sendPacket = new DatagramPacket(responseData, responseData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Sent : " + student.toString());
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
