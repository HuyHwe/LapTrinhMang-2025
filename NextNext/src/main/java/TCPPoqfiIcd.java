import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class TCPPoqfiIcd {
    static String initialMsg = "B22DCCN385;PoqfiIcd";
    static String HOST = "203.162.10.109";
    static int PORT = 2208;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(initialMsg);
            writer.newLine();
            writer.flush();
            System.out.println(initialMsg);

            String data = reader.readLine();
            System.out.println("Received: " + data);
            StringBuilder sb = new StringBuilder();
            for (String s: data.split(", ")) {
                if (s.contains(".edu")) {
                    sb.append(s).append(", ");

                }
            }
            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(sb.length()-1);
            writer.write(sb.toString());
            writer.newLine();
            writer.flush();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
