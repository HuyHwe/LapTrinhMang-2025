/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fwtn8mx5.tcp.pkgbyte.stream;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.abs;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */

public class FWtn8mX5TCPByteStream {
    private static String initialMsg = "B22DCCN385;fWtn8mX5";
    private static String HOST = "203.162.10.109";
    private static int PORT = 2206;
    public static void main(String[] args) {
        // TODO code application logic here
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try{
            socket = new Socket(HOST, PORT);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos.writeUTF(initialMsg);
            dos.flush();
            
            byte[] buffer = new byte[2048];
            int byteRead = dis.read(buffer);
            if (byteRead != -1){
                String data = new String(buffer, 0, byteRead);
                System.out.println(data);
                
                ArrayList<Integer> arr = new ArrayList<>();
                int avg = 0;
                for (String s: data.split(",")) {
                    arr.add(Integer.parseInt(s));
                    avg += arr.getLast();
                }
                
                avg /= arr.size();
                int num1 = arr.get(0), num2 = arr.get(1);
                int min = abs(num1-num2);
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = i+1; j < arr.size(); j++) {
                        int distance = abs(arr.get(i) + arr.get(j) - 2*avg);
                        if (distance < min) {
                            min = distance;
                            num1 = arr.get(i);
                            num2 = arr.get(j);
                        }
                    }
                }
                
                if (num1 > num2){
                    int tmp = num1;
                    num1 = num2;
                    num2 = tmp;
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toString(num1));
                sb.append(",");
                sb.append(Integer.toString(num2));
                dos.writeUTF(sb.toString());
                System.out.println("Sent: " + sb.toString());
                dos.flush();
                
                
                
                
                
            }
            socket.close();
                    
            

            

            dos.flush();
            socket.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(FWtn8mX5TCPByteStream.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
}
}