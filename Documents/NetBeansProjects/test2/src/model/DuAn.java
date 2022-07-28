/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class DuAn implements Serializable{
    private static int index = 10000;
    private int maDuAn;
    private String tenDuAn , kieuDuAn ;
    private double tongKinhPhi;

    public DuAn(String tenDuAn, String kieuDuAn, double tongKinhPhi) {
        this.maDuAn = ++index;
        this.tenDuAn = tenDuAn;
        this.kieuDuAn = kieuDuAn;
        this.tongKinhPhi = tongKinhPhi;
    }

    public static void setIndex(int index) {
        DuAn.index = index;
    }

    public int getMaDuAn() {
        return maDuAn;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public String getKieuDuAn() {
        return kieuDuAn;
    }

    public double getTongKinhPhi() {
        return tongKinhPhi;
    }
    
    public Object[] toObjects(){
        return  new Object[]{maDuAn, tenDuAn, kieuDuAn, tongKinhPhi};
    }
    public boolean trungNhau(DuAn duAn){
        if(duAn.tenDuAn.equals(this.tenDuAn) && duAn.kieuDuAn.equals(this.kieuDuAn)) return true;
        return false;
    }
    
}
