/** [Mã câu hỏi (qCode): nOJffoIm].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,aN”
        -	requestId là chuỗi ngẫu nhiên duy nhất
-	a1 -> aN là N số nguyên ngẫu nhiên
c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> aN và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
d.	Đóng socket và kết thúc chương trình
**/

public class nOJffoImUDP {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2207;
    public static String initMsg = ";B22DCCN385;nOJffoIm";


    public static void main(String args[]) {
        

    }
}
