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
public class NhanVien implements Serializable{
    private static int index = 10000;
    private int maNV;
    private String hoTen , diaChi , chuyenMon ;

    public NhanVien(String hoTen, String diaChi, String chuyenMon) {
        this.maNV = ++index;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.chuyenMon = chuyenMon;
    }

    public static void setIndex(int index) {
        NhanVien.index = index;
    }

    public int getMaNV() {
        return maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }
    
    public Object[] toObjects(){
        return  new Object[]{maNV, hoTen, diaChi, chuyenMon};
    }
    
    
}
