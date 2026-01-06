import RMI.ObjectService;
import RMI.Product;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 [Mã câu hỏi (qCode): zgiYzJtI].  Một chương trình (tạm gọi là RMI server) cung cấp giao diện cho phép triệu gọi từ xa với thông tin như sau:
 -	Giao diện từ xa
 public interface ObjectService extends Remote {
 public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;

 public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
 }
 -	Lớp Product gồm các thông tin: id String, code String, importPrice double, exportPrice double.
 Trường dữ liệu: private static final long serialVersionUID = 20151107L;
 02 hàm khởi dựng
 public Product()
 public Product(id String, String code,double ImportPrice, double ExportPrice)
 Trong đó:
 -	interface ObjectService và lớp Product được viết trong package RMI
 -	Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer: RMIObjectService

 Yêu cầu yêu cầu viết chương trình tại máy trạm (RMI client) thực hiện chuẩn hóa sản phẩm theo thứ tự:
 a.	Triệu gọi phương thức requestObject để lấy về đối tượng sản phẩm cần chuẩn hóa.
 b.	Thực hiện chuẩn hóa đối tượng nhận được theo nguyên tắc:
 - Chuyển mã sản phẩm thành in hoa.
 - Cập nhật giá xuất (exportPrice) bằng giá nhập (importPrice) + 20%

 c.  Triệu gọi phương thức submitObject để gửi dữ liệu đã chuẩn hóa
 d.  Kết thúc chương trình client
**/
public class RMIObject {
    static String HOST = "203.162.10.109";
    static int PORT = 1099;
    static String qCode = "zgiYzJtI";
    static String studentCode = "B22DCCN385";

    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry(HOST, PORT);
        RMI.ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
        Product product = (Product) service.requestObject(studentCode, qCode);
        product.code = product.code.toUpperCase();
        product.exportPrice = product.importPrice*1.2;
        service.submitObject(studentCode, qCode, product);
        System.out.println("Sent: " + product.toString());
    }
}
