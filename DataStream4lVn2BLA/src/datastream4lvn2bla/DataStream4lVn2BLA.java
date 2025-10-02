/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package datastream4lvn2bla;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */
public class DataStream4lVn2BLA {
static String initMsg = "B22DCCN385;4lVn2BLA";
    static int PORT = 2206;
    static String HOST = "203.162.10.109";
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(initMsg);
            dos.flush();
            System.out.println(initMsg);
//            String data = dis.readUTF();
//            System.out.println(data);
            
            byte[] buffer = new byte[100000];
            int byteRead = dis.read(buffer);
            if (byteRead != -1) {
                String data = new String(buffer, 0, byteRead);
                System.out.println(data);
                
                ArrayList
            }
            
        } catch (IOException ex) {
        }
    }
    
}
