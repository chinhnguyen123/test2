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
public class PhanCong implements Serializable{
    private DuAn duAn;
    private NhanVien nhanVien;
    private double soNgayThamGia;
    private String viTri;

    public PhanCong(DuAn duAn, NhanVien nhanVien, double soNgayThamGia, String viTri) {
        this.duAn = duAn;
        this.nhanVien = nhanVien;
        this.soNgayThamGia = soNgayThamGia;
        this.viTri = viTri;
    }

    public DuAn getDuAn() {
        return duAn;
    }

    public void setDuAn(DuAn duAn) {
        this.duAn = duAn;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public double getSoNgayThamGia() {
        return soNgayThamGia;
    }

    public void setSoNgayThamGia(double soNgayThamGia) {
        this.soNgayThamGia = soNgayThamGia;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
    
    public Object[] toObjects(){
        return new Object[]{duAn.getMaDuAn(), duAn.getTenDuAn(), nhanVien.getMaNV(), nhanVien.getHoTen(), soNgayThamGia, viTri};
    }
    
    public boolean trungNhau(PhanCong pc){
        if(pc.getDuAn().getMaDuAn()== this.getDuAn().getMaDuAn() && pc.getNhanVien().getMaNV()==this.getNhanVien().getMaNV()) return  true;
        return false;
    }
    
    
    public boolean dieuKien(PhanCong pc){
        if(pc.getDuAn().getMaDuAn()== this.getDuAn().getMaDuAn() && pc.getNhanVien().getMaNV()==this.getNhanVien().getMaNV() &&(!pc.getViTri().equals(this.getViTri()))) return  true;
        return false;
    }
    
}
