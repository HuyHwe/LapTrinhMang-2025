import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TCPRAjWmFwy {
    static String initMsg = "B22DCCN385;RAjWmFwy";
    static int PORT = 2206;
    static String HOST = "203.162.10.109";

    public static void main(String args[]) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeUTF(initMsg);
        dos.flush();
        System.out.println("Sent: " + initMsg);

        int num = dis.readInt();

        System.out.println("get:" + Integer.toString(num));
        int count = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        while (num != 1) {
            count++;
            arr.add(num);
            if (num % 2 == 0) num /= 2;
            else num = num*3 + 1;

        }
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(Integer.toString(i)).append(" ");

        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("; ").append(Integer.toString(count)).append(";");
        dos.writeUTF(sb.toString());
        dos.flush();
        System.out.println("Sent: " + sb.toString());

        socket.close();


    }
}
