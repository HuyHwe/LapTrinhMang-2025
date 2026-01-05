/*
[Mã câu hỏi (qCode): DXw5IAQA].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
Giao diện từ xa:
public interface CharacterService extends Remote {
public String requestCharacter(String studentCode, String qCode) throws RemoteException;
public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}
Trong đó:
• Interface CharacterService được viết trong package RMI.
• Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi văn bản đầu vào".
b. Thực hiện thao tác đếm tần số xuất hiện của từng ký tự trong chuỗi đầu vào. Kết quả trả về là danh sách các ký tự kèm theo số lần xuất hiện của mỗi ký tự.
Ví dụ: Chuỗi ban đầu "Hello world" -> Kết quả đếm tần số ký tự: {"H": 1, "e": 1, "l": 3, "o": 2, " ": 1, "w": 1, "r": 1, "d": 1}.
c. Triệu gọi phương thức submitCharacter để gửi kết quả đếm tần số ký tự trở lại server dưới dạng chuỗi kết quả đã được định dạng.
d. Kết thúc chương trình client.

*/

import RMI.CharacterService;
import RMI.DataService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RMICharacter {
    public static String HOST = "203.162.10.109";
    public static int PORT = 1099;
    public static String qCode = "DXw5IAQA";
    public static String studentCode = "B22DCCN385";
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(HOST, PORT);
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String data = service.requestCharacter(studentCode, qCode);
        System.out.println(data);

        LinkedHashMap<Character, Integer> map = new LinkedHashMap<Character, Integer>();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);
        }
        StringBuilder sb = new StringBuilder("{");
        for (char c : map.keySet()) {
            sb.append("\"").append(c).append("\": ").append(Integer.toString(map.get(c))).append(", ");
        }

        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");

        service.submitCharacter(studentCode, qCode, sb.toString());




    }
}
