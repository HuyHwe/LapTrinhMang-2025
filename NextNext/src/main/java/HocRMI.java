/*
[Mã câu hỏi (qCode): 87YIiE0c].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
public interface DataService extends Remote {
public Object requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
Trong đó:
•	Interface DataService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một số nguyên dương amount từ server, đại diện cho số tiền cần đạt được.
b. Sử dụng thuật toán xếp đồng xu với các mệnh giá cố định [1, 2, 5, 10] để xác định số lượng đồng xu tối thiểu cần thiết để đạt được số tiền amount. Nếu không thể đạt được số tiền đó với các mệnh giá hiện có, trả về -1.
Ví dụ: Với amount = 18 và mệnh giá đồng xu cố định [1, 2, 5, 10], kết quả là 4 (18 = 10 + 5 + 2 + 1). Chuỗi cần gửi lên server là: 4; 10,5,2,1
c. Triệu gọi phương thức submitData để gửi chuỗi (kiểu String) chứa kết quả số lượng đồng xu tối thiểu và giá trị các đồng xu tương ứng  trở lại server.
d. Kết thúc chương trình client.
*/

import RMI.DataService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HocRMI {
    public static String HOST = "203.162.10.109";
    public static int PORT = 1099;
    public static String qCode = "87YIiE0c";
    public static String sCode = "B22DCCN385";
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(HOST, PORT);

        DataService service = (DataService) registry.lookup("RMIDataService");
        Object data = service.requestData(sCode, qCode);
        int amount = (int) data;
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        while (amount != 0) {
            while (amount >= 10) {
                amount -= 10;
                sb.append("10,");
                cnt++;
            }
            while (amount >= 5) {
                amount -= 5;
                sb.append("5,");
                cnt++;

            }
            while (amount >= 2) {
                amount -= 2;
                cnt++;
                sb.append("2,");
            }
            while (amount >= 1) {
                amount -= 1;
                cnt++;
                sb.append("1,");
            }

        }
        sb.deleteCharAt(sb.length()-1);
        sb.insert(0, Integer.toString(cnt) + "; ");
        System.out.println("Sent : " + sb.toString());
        service.submitData(sCode, qCode, sb.toString());


    }







}
