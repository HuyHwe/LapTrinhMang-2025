/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wbubc0kc.tcp.pkgbyte.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.out;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */


public class WBUBC0kCTCPByteStream {
    private static String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1) {   // đọc từng byte
            if (b == '\r') continue;      // bỏ qua CR (Windows: \r\n)
            if (b == '\n') break;         // kết thúc khi gặp LF
            sb.append((char) b);          // ghép ký tự
        }
        if (sb.length() == 0 && b == -1) return null; // EOF mà chưa có dữ liệu
        return sb.toString().trim();
    }
    
    private final static String HOST = "203.162.10.109";
    private final static int PORT = 2206;
    static Socket socket = null;
    static DataOutputStream writer = null;
    static DataInputStream reader = null;
    static String code = "B22DCCN385;wBUBC0kC";
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            socket = new Socket(HOST, PORT);
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
            
            String numbersLine = readLine(reader);
            System.out.println("Received: " + numbersLine);

            // c) Tính tổng
            long sum = sumPipeSeparatedIntegers(numbersLine);

            // Gửi kết quả
            String result = sum + "\n";
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("Sent sum: " + sum);
            String mes = reader.readUTF();
            System.out.println(mes);
            
        } catch (IOException ex) {
            Logger.getLogger(WBUBC0kCTCPByteStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static long sumPipeSeparatedIntegers(String line) {
        if (line == null || line.isEmpty()) return 0L;
        String[] parts = line.split("\\|");
        long total = 0L;
        for (String p : parts) {
            if (!p.isBlank()) {
                total += Long.parseLong(p.trim());
            }
        }
        return total;
    }
}
