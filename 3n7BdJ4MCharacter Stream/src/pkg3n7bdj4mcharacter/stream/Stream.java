/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg3n7bdj4mcharacter.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenduchuy
 */
public class Stream {
    static String initialMsg = "B22DCCN385;3n7BdJ4M";
    static String HOST = "203.162.10.109";
    static int PORT = 2208;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            Socket socket = null;
            BufferedWriter writer = null;
            BufferedReader reader = null;
            
            
            socket = new Socket(HOST, PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            writer.write(initialMsg);
            writer.write("\r\n");   // or "\n"; many line protocols prefer CRLF
            writer.flush();
            System.out.print("Sent: " + initialMsg);
            
            String received = reader.readLine();
            String arr[] = received.split(" ");
            ArrayList<String> arrList = new ArrayList<>();
            for (String s: arr) arrList.add(s);
            SortLength cmp = new SortLength();
            arrList.sort(cmp);
            StringBuilder sb = new StringBuilder();
            for (String s: arrList){
                sb.append(s);
                if (!s.equals(arrList.getLast())) sb.append(", ");
            }
            
            writer.write(sb.toString());
            System.out.println("Wrote: " + sb.toString());
            writer.write("\r\n");   // or "\n"; many line protocols prefer CRLF
            writer.flush();
            
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

class SortLength implements Comparator {
  public int compare(Object obj1, Object obj2) {
    // Make sure that the objects are Car objects
    String a = (String) obj1;
    String b = (String) obj2;
    
    // Compare the objects
    if (a.length() < b.length()) return -1; 
    else return 1;
  }
}