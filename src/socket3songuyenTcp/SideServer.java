/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket3songuyenTcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unmatched TaiNguyen
 */
public class SideServer {
    
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
            ServerSocket ss = new ServerSocket(34567);
            int chon;
            int[] a = new int[3];

               Socket sc = ss.accept();
               DataOutputStream gui = new DataOutputStream(sc.getOutputStream());
               DataInputStream nhan = new DataInputStream(sc.getInputStream());
                
                for (int i = 0; i < 3; i++) {
                    a[i] = nhan.readInt();
                    System.out.println(a[i]);
                }
                
               do{
              
                gui.writeUTF(Menu);
                
                chon = nhan.readInt();
                
                switch(chon){
                    case 1:{
                        int dem  = 0;
                        for (int i = 0; i < 3; i++) {
                            if(a[i]>=0) dem++;
                        }
                        gui.writeInt(dem);
                        break;
                    }
                    case 2:{
                        List<Integer> ls = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            if(KiemTraNguyenTo(a[i])) ls.add(a[i]);
                        }
                        gui.writeInt(ls.size());
                        for(Integer i : ls){
                            gui.writeInt(i);
                        }
                        break;
                    }
                }
               
            }while(chon != 3);
            
        } catch (IOException ex) {
            Logger.getLogger(SideServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
