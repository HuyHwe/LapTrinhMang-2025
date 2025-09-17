/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package o3k3x9ff.tcp.data.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */
public class O3K3X9FfTCPDataStream {

    /**
     * @param args the command line arguments
     */
    private static String initialMsg = "B22DCCN385;O3K3X9Ff";
    private static String HOST = "203.162.10.109";
    private static int PORT = 2207;
    public static void main(String[] args) {
        // TODO code application logic here
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try{
            socket = new Socket(HOST, PORT);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            dos.writeUTF(initialMsg);
            dos.flush();
            System.out.println("Sent: " + initialMsg + "\n");
            
            int a = dis.readInt();
            int b = dis.readInt();
            int sum = a + b;
            int prod = a * b;
            dos.writeInt(sum);
            dos.writeInt(prod);
            System.out.println("Prod: ");            System.out.println(prod);
            System.out.println("Sum: ");            System.out.println(sum);

            

            dos.flush();
            socket.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(O3K3X9FfTCPDataStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
