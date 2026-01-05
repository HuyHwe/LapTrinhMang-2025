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

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI87YIiE0c {
    public static String HOST = "203.162.10.109";

    private static final int[] COINS = {10, 5, 2, 1};

    public static void main(String[] args) {
        String studentCode = "B22DCCN385";     // thay bằng mã sinh viên của bạn
        String qCode = "87YIiE0c";

        try {
            // 1. Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry(HOST, 1099);

            // 2. Lookup remote object
            DataService service =
                    (DataService) registry.lookup("RMIDataService");

            // 3. Gọi requestData để nhận amount
            Object response = service.requestData(studentCode, qCode);
            int amount = (int) response;

            // 4. Giải bài toán xếp đồng xu
            String result = solveCoinChange(amount);

            // 5. Gửi kết quả về server
            service.submitData(studentCode, qCode, result);

            // 6. Kết thúc
            System.out.println("Đã gửi kết quả: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String solveCoinChange(int amount) {
        List<Integer> usedCoins = new ArrayList<>();
        int remaining = amount;

        for (int coin : COINS) {
            while (remaining >= coin) {
                remaining -= coin;
                usedCoins.add(coin);
            }
        }

        // Không thể xếp
        if (remaining != 0) {
            return "-1";
        }

        // Xây dựng chuỗi kết quả
        StringBuilder sb = new StringBuilder();
        sb.append(usedCoins.size()).append("; ");

        for (int i = 0; i < usedCoins.size(); i++) {
            sb.append(usedCoins.get(i));
            if (i < usedCoins.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
