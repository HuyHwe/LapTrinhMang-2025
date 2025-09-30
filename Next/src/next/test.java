package next;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class test {

    private static final String SERVER_IP = "203.162.10.109"; // Thay thế bằng IP Server thực tế
    private static final int SERVER_PORT = 2207;
    private static final String STUDENT_CODE = "B22DCCN385"; // Thay thế bằng Mã sinh viên của bạn
    private static final String QUESTION_CODE = "nOJffoIm";
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        try {
            // 1. Khởi tạo DatagramSocket và địa chỉ Server
            clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            System.out.println("Client đang chạy...");

            // --- BƯỚC a: Gửi thông điệp chứa mã sinh viên và mã câu hỏi ---
            String initialMessage = ";" + STUDENT_CODE + ";" + QUESTION_CODE;
            byte[] sendData = initialMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
            System.out.println("Gửi: " + initialMessage);

            // --- BƯỚC b: Nhận thông điệp từ Server ---
            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Nhận: " + receivedMessage);

            int minVal = Integer.MAX_VALUE;
            int maxVal = Integer.MIN_VALUE;

            ArrayList<String> arr = new ArrayList<>();
            String requestId = "";
            int cnt = 0;
            for (String s : receivedMessage.split(";")) {
                if (cnt == 0) { requestId = s; cnt++ ; }
                else {
                    arr.add(s);
                }
            }

            // --- BƯỚC c: Gửi thông điệp kết quả (requestId;max,min) lên Server ---
            String resultMessage = requestId + ";" + maxVal + "," + minVal;
            sendData = resultMessage.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
            System.out.println("Gửi kết quả: " + resultMessage);
            System.out.println("Giá trị Max: " + maxVal + ", Min: " + minVal);

        } catch (IOException e) {
            System.err.println("Lỗi I/O hoặc Socket: " + e.getMessage());
        } finally {
            // --- BƯỚC d: Đóng socket và kết thúc chương trình ---
            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Đã đóng socket và kết thúc Client.");
            }
        }
    }
}