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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unmatched TaiNguyen
 */
public class ClientSide {
      public static DatagramPacket GetPacket(Object obj,String Adress,int Port){
        try {
            ByteArrayOutputStream outp = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(outp);
            out.writeObject(obj);
            
            byte[] m = outp.toByteArray();
            
            DatagramPacket p = new DatagramPacket(m,m.length,InetAddress.getByName(Adress),Port);
            
            return p;
            
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static Object GetObject (DatagramPacket p){
        try {
            ByteArrayInputStream inp = new ByteArrayInputStream(p.getData());
            ObjectInput in = new ObjectInputStream(inp);
            
            return in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static void main(String[] args) {
        String address = "localhost";
        int port = 34568;
          try {
              DatagramSocket pp = new DatagramSocket();
              
             Scanner scan = new Scanner(System.in);
            
            System.out.println("Nhap so 1: ");
            int a  = Integer.parseInt(scan.nextLine());
            
            System.out.println("Nhap so 2: ");
            int b  = Integer.parseInt(scan.nextLine());
            System.out.println("Nhap so 3: ");
            int c  = Integer.parseInt(scan.nextLine());
            
            pp.send(GetPacket(a,address,port));
            pp.send(GetPacket(b,address,port));
            pp.send(GetPacket(c,address,port));
            byte[] m=null;
            int chon =3;
            do{
                m = new byte[1024];
                DatagramPacket p1= new DatagramPacket(m,m.length);
                pp.receive(p1);
                
                String menu = (String) GetObject(p1);
                System.out.println("--------------Menu------------------");
                System.out.println(menu);
                chon = Integer.parseInt(scan.nextLine());
                
                //gui.writeInt(chon);
                 pp.send(GetPacket(chon,address,port));
            
            switch(chon){
                case 1:{
                    m = new byte[1024];
                    p1= new DatagramPacket(m,m.length);
                    pp.receive(p1);
                    int SlSoDuong = (int) GetObject(p1); //nhan.readInt();
                    System.out.println("--------------@@@-------------------");
                    System.out.println("So luong so duong la : "+SlSoDuong);
                    break;
                }
                case 2:{
                    m = new byte[1024];
                    p1= new DatagramPacket(m,m.length);
                    pp.receive(p1);
                    int SlSNT = (int) GetObject(p1);//nhan.readInt();
                    System.out.println("--------------@@@-------------------");

                    if(SlSNT == 0) {
                        System.out.println("Khong co so nguyen to nao");
                    }else{
                        System.out.print("Cac so nguyen to la:");
                        for (int i = 0; i < SlSNT; i++) {
                            m = new byte[1024];
                            p1= new DatagramPacket(m,m.length);
                            pp.receive(p1);
                            int n = (int) GetObject(p1);//nhan.readInt();
                            System.out.print(n+",");
                        }
                        System.out.println("\n");
                    }
                    break;
                }
            }
            }while(chon != 3);

          } catch (SocketException ex) {
              Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        
    }
}
