import java.io.IOException;
import java.net.*;

public class UDPB8AouDxA {
    static String initMsg = ";B22DCCN385;B8AouDxA";
    static int PORT = 2208;
    static String HOST = "203.162.10.109";

    public static void main(String[] args) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName(HOST);
            byte[] sendData = initMsg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send: " + initMsg);

            byte[] buffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            client.receive(receivedPacket);
            String data = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println(data);
            String code = data.split(";")[0];
            String line = data.split(";")[1];
            StringBuilder result = new StringBuilder(code);
            result.append(";");
            for (String s: line.split(" ")) {
                StringBuilder sb = new StringBuilder(s.toLowerCase());
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                result.append(sb.toString()).append(" ");
            }

            byte[] sendResult = result.toString().getBytes();
            sendPacket = new DatagramPacket(sendResult, sendResult.length, address, PORT);
            client.send(sendPacket);
            System.out.println("Send " + result.toString());
            client.close();


        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
