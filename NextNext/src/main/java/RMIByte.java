import RMI.ByteService;
import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 [Mã câu hỏi (qCode): JdsK5fVt].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
 Giao diện từ xa:
 public interface ByteService extends Remote {
 public byte[] requestData(String studentCode, String qCode) throws RemoteException;
 public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
 }
 Trong đó:
 •	Interface ByteService được viết trong package RMI.
 •	Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
 Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
 a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, đại diện cho một chuỗi văn bản ASCII.
 b. Thực hiện mã hóa Caesar cho mảng dữ liệu nhị phân bằng cách dịch chuyển mỗi byte trong mảng đi một số bước cố định trong bảng mã ASCII. Số bước dịch chuyển là số ký tự ASCII trong mảng dữ liệu.
 Ví dụ: Nếu dữ liệu nhị phân nhận được là [72, 101, 108, 108, 111] (tương ứng với chuỗi "Hello"), chương trình sẽ thực hiện mã hóa Caesar với độ dịch là 5. Kết quả mã hóa là mảng [77, 108, 113, 113, 116], tương ứng với chuỗi "Mlqqt".
 c. Triệu gọi phương thức submitData để gửi mảng dữ liệu đã được mã hóa bằng Caesar trở lại server.
 d. Kết thúc chương trình client.
**/
public class RMIByte {
    public static String HOST = "203.162.10.109";
    public static int PORT = 1099;
    public static String qCode = "JdsK5fVt";
    public static String sCode = "B22DCCN385";

    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry(HOST, PORT);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] buffer = service.requestData(sCode, qCode);
        String data = new String(buffer);
        int shift = buffer.length;
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) (buffer[i] + shift);
        }

        service.submitData(sCode,qCode, buffer);





    }
}
