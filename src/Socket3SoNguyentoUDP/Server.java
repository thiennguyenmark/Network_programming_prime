/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket3SoNguyentoUDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioDataStream;

/**
 *
 * @author Unmatched TaiNguyen
 */
public class Server {
    public static DatagramPacket GetPacket(Object obj,String Adress,int Port){
        try {
            ByteArrayOutputStream outp = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(outp);
            out.writeObject(obj);
            
            byte[] m = outp.toByteArray();
            
            DatagramPacket p = new DatagramPacket(m,m.length,InetAddress.getByName(Adress),Port);
            
            return p;
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static Object GetObject (DatagramPacket p){
        try {
            ByteArrayInputStream inp = new ByteArrayInputStream(p.getData());
            ObjectInput in = new ObjectInputStream(inp);
            
            return in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static DatagramPacket getPK(){
         byte[] m = new byte[1024];
         return new DatagramPacket(m, m.length);
    }
    private  static  String Menu = "\n1.So luong so duong trong ba so"
                                    +"\n2. Cac so nguyen trong ba so"
                                    +"\n3. Thoat";
     private static  boolean KiemTraNguyenTo(int b){
        if(b==0 || b==1) return false;
        int dem = 0;
        for (int i = 1; i <= Math.sqrt(b); i++) {
            if(b%i==0) dem ++;
        }
        if(dem == 1) return true;
        else return false;
    }
    public static void main(String[] args) {
        try {
            DatagramSocket pp = new DatagramSocket(34568);
            
            int[] a = new int[3];
            DatagramPacket p = null ;
            for (int i = 0; i < 3; i++) {
                byte[] m = new byte[1024];
                p = new DatagramPacket(m, m.length);
                pp.receive(p);
                a[i] = (int) GetObject(p);
                System.out.println(a[i]);
            }
            int chon = 3;
            do{
               pp.send(GetPacket(Menu,p.getAddress().getHostName(), p.getPort()));
               
               DatagramPacket p1 = getPK();
               pp.receive(p1);
               
               chon = (int) GetObject(p1);
                
                switch(chon){
                    case 1:{
                        int dem  = 0;
                        for (int i = 0; i < 3; i++) {
                            if(a[i]>=0) dem++;
                        }
                       // gui.writeInt(dem);
                       p1 = GetPacket(dem,p.getAddress().getHostName(),p.getPort());
                       pp.send(p1);
                        break;
                    }
                    case 2:{
                        List<Integer> ls = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            if(KiemTraNguyenTo(a[i])) ls.add(a[i]);
                        }
                        //gui.writeInt(ls.size());
                        p1 = GetPacket(ls.size(),p.getAddress().getHostName(),p.getPort());
                        pp.send(p1);
                        for(Integer i : ls){
                            //gui.writeInt(i);
                             p1 = GetPacket(i,p.getAddress().getHostName(),p.getPort());
                             pp.send(p1);
                        }
                        break;
                    }
                }
               
            }while(chon != 3);
            
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
