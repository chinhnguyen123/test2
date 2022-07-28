/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class IOFile {
    public static void input(ArrayList list, String name){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));){
            Object object;
            while((object=ois.readObject())!=null){
                list.add(object);
            }
            ois.close();
        } catch (FileNotFoundException ex) {
            System.out.println("khong tim thay file");
        } catch (IOException ex) {
            System.out.println("file da duoc doc trong object");
        } catch (ClassNotFoundException ex) {
          
        }
    }
    
    public static void output(ArrayList list, String name){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));){
            for(Object object : list){
                oos.writeObject(object);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("khong tim thay file");
        } catch (IOException ex) {
            
        }
        
    }
    
}
