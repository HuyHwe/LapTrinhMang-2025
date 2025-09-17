/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package B4yRerhp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */
public class App2 {

    static final int PORT = 2208;
    static final String HOST = "203.162.10.109";
   
    
    public static void main(String[] args) {
        // TODO code application logic here
        String code = "B22DCCN385;B4yRerhp";
        try {
            Socket socket = null;
            BufferedWriter writer = null;
            BufferedReader reader = null;
            
            
            socket = new Socket(HOST, PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            writer.write(code);
            writer.newLine();
            writer.flush();
            
            System.out.println("Sent init message: " + code);
            String str = reader.readLine();
            System.out.println("Server msg: " + str);
            String[] arr = str.split(",");
            ArrayList<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder("");
            for (String s : arr){
                if(s.endsWith(".edu")) {
                    sb.append(s);
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length()-1);
            System.out.println(sb.toString());
            writer.write(sb.toString());
            writer.newLine();
            writer.flush();
            socket.close();
            
            
            
            
            
            
        } catch(UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(App2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
