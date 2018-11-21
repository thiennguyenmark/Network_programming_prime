/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket3songuyenTcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unmatched TaiNguyen
 */
public class SideClient {
    public static void main(String[] args) {
        try {
            int chon ;
             
            Socket sc = new Socket("42.115.141.161",34567);
            DataOutputStream gui = new DataOutputStream(sc.getOutputStream());
            DataInputStream nhan = new DataInputStream(sc.getInputStream());
            
            Scanner scan = new Scanner(System.in);
            
            System.out.println("Nhap so 1: ");
            int a  = Integer.parseInt(scan.nextLine());
            
            System.out.println("Nhap so 2: ");
            int b  = Integer.parseInt(scan.nextLine());
            System.out.println("Nhap so 3: ");
            int c  = Integer.parseInt(scan.nextLine());
            
            gui.writeInt(a);
            gui.writeInt(b);
            gui.writeInt(c);
            
            do{
            String Menu = nhan.readUTF();
            System.out.println("--------------Menu------------------");

            System.out.println(Menu);
            chon = Integer.parseInt(scan.nextLine());
            
            gui.writeInt(chon);
            
            switch(chon){
                case 1:{
                    int SlSoDuong = nhan.readInt();
                    System.out.println("--------------@@@-------------------");
                    System.out.println("So luong so duong la : "+SlSoDuong);
                    break;
                }
                case 2:{
                    int SlSNT = nhan.readInt();
                    System.out.println("--------------@@@-------------------");

                    if(SlSNT == 0) {
                        System.out.println("Khong co so nguyen to nao");
                    }else{
                        System.out.print("Cac so nguyen to la:");
                        for (int i = 0; i < SlSNT; i++) {
                            int n = nhan.readInt();
                            System.out.print(n+",");
                        }
                        System.out.println("\n");
                    }
                    break;
                }
            }
            
            }while(chon != 3);
            
        } catch (IOException ex) {
            Logger.getLogger(SideClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
