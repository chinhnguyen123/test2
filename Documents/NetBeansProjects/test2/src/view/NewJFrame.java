/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import model.*;
import controller.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public static final String duAnFile = "DA.TXT", nhanvienFile= "NV.TXT", phanCongFile = "PHANCONG.TXT";
    ArrayList<DuAn> duAnsList;
    ArrayList<NhanVien> nhanViensList;
    ArrayList<PhanCong> phanCongsList;
    
    DefaultTableModel duAnDefaultTableModel, nhanVienDefaultTableModel, phanCongDefaultTableModel;
    
    public NewJFrame() {
        initComponents();
        duAnsList = new ArrayList<>();
        nhanViensList = new ArrayList<>();
        phanCongsList = new ArrayList<>();
        
        duAnDefaultTableModel = (DefaultTableModel)duAnTable.getModel();
        nhanVienDefaultTableModel = (DefaultTableModel)nhanVienTable.getModel();
        phanCongDefaultTableModel = (DefaultTableModel)phanCongTable.getModel();
        
        loadDaTaToTable();
        
        loadDataToBox();
        
        
        
        themDuAnBTN.addActionListener((ae) -> {
            String tenDuAn = tenDuAnTF.getText();
            String kieuDuAn = kieuDuAnCB.getItemAt(kieuDuAnCB.getSelectedIndex());
            String tongKinhPhi = tongKinhPhiTF.getText();
            if(tenDuAn.equals("") || kieuDuAn.equals("")|| tongKinhPhi.equals("")){
                JOptionPane.showMessageDialog(null, "Khong duoc de trong");
                return;
            }
            
            int temp = duAnsList.size()-1;
            if(temp !=-1){
                DuAn.setIndex(duAnsList.get(temp).getMaDuAn());
            }
            
            try{
                double t = Double.parseDouble(tongKinhPhi);
                if(t<0){
                    JOptionPane.showMessageDialog(null, "tong kinh phi phai > 0");
                return;
                }
                 DuAn duAn = new DuAn(tenDuAn, kieuDuAn, t);
                 for(DuAn da:duAnsList){
                     if(duAn.trungNhau(da)){
                          JOptionPane.showMessageDialog(null, "khong duoc trung nhau");
                return;
                     }
                 }
                 duAnDefaultTableModel.addRow(duAn.toObjects());
                 duAnsList.add(duAn);
                 loadDataToBox();
                
            }catch(NumberFormatException e){
                 JOptionPane.showMessageDialog(null, "tong kinh phi la so");
                return;
            }
           
        });
        
        themNhanVienBTN.addActionListener((ae) -> {
            String hoTen = tenNhanVienTF.getText();
            String diaChi = diaChiTF.getText();
            String chuyenMon = chuyenMonTF.getText();
            
            if(hoTen.equals("") || diaChi.equals("") || chuyenMon.equals("")){
                 JOptionPane.showMessageDialog(null, "Khong duoc de trong");
                return;
            }
            int tmp = nhanViensList.size()-1;
            if(tmp!=-1){
                NhanVien.setIndex(nhanViensList.get(tmp).getMaNV());
            }
            NhanVien nhanVien = new NhanVien(hoTen, diaChi, chuyenMon);
            nhanVienDefaultTableModel.addRow(nhanVien.toObjects());
            nhanViensList.add(nhanVien);
            loadDataToBox();
        });
        
        themPhanCongBTN.addActionListener((ae) -> {
            int duAnNo = maDuAnCB.getSelectedIndex();
            int nhanVienNo = maNhanVienCB.getSelectedIndex();
            
            NhanVien nhanVien = nhanViensList.get(nhanVienNo);
            DuAn duAn = duAnsList.get(duAnNo);
            
            String soNgay = soNgayThamGiaTF.getText();
            String viTri = viTriTF.getText();
            
            if(viTri.equals("") || soNgay.equals("")){
                 JOptionPane.showMessageDialog(null, "Khong duoc de trong");
                return;
            }
            try{
                double s = Double.parseDouble(soNgay);
                if(s <0){
                     JOptionPane.showMessageDialog(null, "so ngay phai > 0");
                return;
                }
                 PhanCong phanCong = new PhanCong(duAn, nhanVien,  s, viTri);
                 for(PhanCong pc:phanCongsList ){
                     if(phanCong.dieuKien(pc)){
                         JOptionPane.showMessageDialog(null, "cung mot nhan vien, cung mot du an, khong the lam 2 vi tri khac nhau");
                         return;
                     }
                 }
                 phanCongDefaultTableModel.addRow(phanCong.toObjects());
                 phanCongsList.add(phanCong);
                
            }catch(NumberFormatException e){
                 JOptionPane.showMessageDialog(null, "so ngay phai la so");
                return;
            }
           
        });
        
        sortBTN.addActionListener((ae) -> {
            String choise = sortCB.getItemAt(sortCB.getSelectedIndex());
            
            if (choise.equals("theo ho ten nhan vien")) {
                Collections.sort(phanCongsList, (o1, o2) -> {
                    return o1.getNhanVien().getHoTen().compareTo(o2.getNhanVien().getHoTen());
                });
            } 
            else {
                  Collections.sort(phanCongsList, new Comparator<PhanCong>() {
                      @Override
                      public int compare(PhanCong t, PhanCong t1) {
                          double x = t1.getSoNgayThamGia() - t.getSoNgayThamGia();
                          if(x>0) return 1;
                          else if(x==0) return 0;
                          else return -1;
                          
                      }
                  });
            }
            
            phanCongDefaultTableModel.setRowCount(0);
            for(PhanCong pc: phanCongsList){
                phanCongDefaultTableModel.addRow(pc.toObjects());
            }
        });
        
        seachBTN.addActionListener((ae) -> {
            DefaultTableModel searchDefaultTableModel = (DefaultTableModel)searchTable.getModel();
            String choice = searchCB.getItemAt(searchCB.getSelectedIndex());
            String search = searchTF.getText();
            searchDefaultTableModel.setRowCount(0);
            
            if(choice.equals("tim kiem theo ten")){
                for(PhanCong pc:phanCongsList){
                    if(pc.getNhanVien().getHoTen().contains(search)){
                        searchDefaultTableModel.addRow(pc.toObjects());
                    }
                }
            }
                else{
                for(PhanCong pc:phanCongsList){
                    if(pc.getDuAn().getTenDuAn().contains(search)){
                        searchDefaultTableModel.addRow(pc.toObjects());
                    }
                }
            }
            
        });
        
        saveDataToFile(luuDuAnBTN, duAnsList, duAnFile);
        saveDataToFile(luuNhanVienBTN, nhanViensList, nhanvienFile);
        saveDataToFile(luuPhanCongBTN, phanCongsList, phanCongFile);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tenDuAnTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        kieuDuAnCB = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        tongKinhPhiTF = new javax.swing.JTextField();
        themDuAnBTN = new javax.swing.JButton();
        luuDuAnBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        duAnTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tenNhanVienTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        diaChiTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        chuyenMonTF = new javax.swing.JTextField();
        themNhanVienBTN = new javax.swing.JButton();
        luuNhanVienBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        nhanVienTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        maDuAnCB = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        maNhanVienCB = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        soNgayThamGiaTF = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        viTriTF = new javax.swing.JTextField();
        themPhanCongBTN = new javax.swing.JButton();
        luuPhanCongBTN = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        phanCongTable = new javax.swing.JTable();
        sortCB = new javax.swing.JComboBox<>();
        sortBTN = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        searchCB = new javax.swing.JComboBox<>();
        searchTF = new javax.swing.JTextField();
        seachBTN = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ten du an");

        jLabel2.setText("kieu du an");

        kieuDuAnCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nho", "trung binh", "lon" }));

        jLabel3.setText("tong kinh phi");

        tongKinhPhiTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tongKinhPhiTFActionPerformed(evt);
            }
        });

        themDuAnBTN.setText("them ");

        luuDuAnBTN.setText("luu");

        duAnTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ma du an", "ten du an", "kieu du an", "tong chi phi"
            }
        ));
        jScrollPane1.setViewportView(duAnTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(tenDuAnTF)
                            .addComponent(kieuDuAnCB, 0, 159, Short.MAX_VALUE)
                            .addComponent(tongKinhPhiTF)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(themDuAnBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(luuDuAnBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tenDuAnTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(kieuDuAnCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(tongKinhPhiTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(themDuAnBTN))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addComponent(luuDuAnBTN)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("du an", jPanel1);

        jLabel4.setText("ten nhan vien");

        jLabel5.setText("dia chi");

        jLabel6.setText("chuyen mon");

        chuyenMonTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chuyenMonTFActionPerformed(evt);
            }
        });

        themNhanVienBTN.setText("them");

        luuNhanVienBTN.setText("luu");

        nhanVienTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ma nhan vien", "ten nhan vien", "dia chi", "chuyen mon"
            }
        ));
        jScrollPane2.setViewportView(nhanVienTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(82, 82, 82)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addComponent(tenNhanVienTF, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(chuyenMonTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(diaChiTF, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(luuNhanVienBTN)
                            .addComponent(themNhanVienBTN))
                        .addGap(117, 117, 117)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenNhanVienTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(diaChiTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(chuyenMonTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(themNhanVienBTN)
                .addGap(56, 56, 56)
                .addComponent(luuNhanVienBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("nhan vien", jPanel2);

        jLabel7.setText("ma du an");

        jLabel8.setText("ma nhan vien");

        jLabel9.setText("so ngay tham gia");

        soNgayThamGiaTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soNgayThamGiaTFActionPerformed(evt);
            }
        });

        jLabel10.setText("vi tri");

        viTriTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viTriTFActionPerformed(evt);
            }
        });

        themPhanCongBTN.setText("them");

        luuPhanCongBTN.setText("luu");

        phanCongTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ma du an", "ten du an", "ma nhan vien", "ten nhan vien", "so ngay tham gia", "vi tri"
            }
        ));
        jScrollPane3.setViewportView(phanCongTable);

        sortCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "theo ho ten nhan vien", "theo so ngay tham gia" }));

        sortBTN.setText("sapxep");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(viTriTF, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7)
                                .addComponent(jLabel9)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(maDuAnCB, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(maNhanVienCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(soNgayThamGiaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sortCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(themPhanCongBTN)
                                .addGap(38, 38, 38)
                                .addComponent(luuPhanCongBTN)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(sortBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(maDuAnCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(maNhanVienCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(soNgayThamGiaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viTriTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(themPhanCongBTN)
                            .addComponent(luuPhanCongBTN))
                        .addGap(18, 18, 18)
                        .addComponent(sortCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sortBTN)
                .addContainerGap())
        );

        jTabbedPane1.addTab("phan cong", jPanel3);

        searchCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tim kiem theo ten", "tim kiem theo du an" }));
        searchCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCBActionPerformed(evt);
            }
        });

        seachBTN.setText("search");
        seachBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachBTNActionPerformed(evt);
            }
        });

        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ma du an", "ten du an", "ma nhan vien", "ten nhan vien", "so ngay tham gia", "vi tri"
            }
        ));
        jScrollPane4.setViewportView(searchTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(seachBTN)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(searchCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seachBTN))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tongKinhPhiTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tongKinhPhiTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tongKinhPhiTFActionPerformed

    private void chuyenMonTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chuyenMonTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chuyenMonTFActionPerformed

    private void soNgayThamGiaTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soNgayThamGiaTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soNgayThamGiaTFActionPerformed

    private void viTriTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viTriTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viTriTFActionPerformed

    private void searchCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchCBActionPerformed

    private void seachBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachBTNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seachBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField chuyenMonTF;
    private javax.swing.JTextField diaChiTF;
    private javax.swing.JTable duAnTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> kieuDuAnCB;
    private javax.swing.JButton luuDuAnBTN;
    private javax.swing.JButton luuNhanVienBTN;
    private javax.swing.JButton luuPhanCongBTN;
    private javax.swing.JComboBox<String> maDuAnCB;
    private javax.swing.JComboBox<String> maNhanVienCB;
    private javax.swing.JTable nhanVienTable;
    private javax.swing.JTable phanCongTable;
    private javax.swing.JButton seachBTN;
    private javax.swing.JComboBox<String> searchCB;
    private javax.swing.JTextField searchTF;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField soNgayThamGiaTF;
    private javax.swing.JButton sortBTN;
    private javax.swing.JComboBox<String> sortCB;
    private javax.swing.JTextField tenDuAnTF;
    private javax.swing.JTextField tenNhanVienTF;
    private javax.swing.JButton themDuAnBTN;
    private javax.swing.JButton themNhanVienBTN;
    private javax.swing.JButton themPhanCongBTN;
    private javax.swing.JTextField tongKinhPhiTF;
    private javax.swing.JTextField viTriTF;
    // End of variables declaration//GEN-END:variables

    private void loadDaTaToTable() {
     IOFile.input(duAnsList, duAnFile);
     IOFile.input(nhanViensList, nhanvienFile);
     IOFile.input(phanCongsList, phanCongFile);
     
     for(DuAn duAn: duAnsList){
         duAnDefaultTableModel.addRow(duAn.toObjects());
     }
     
     for(NhanVien nhanVien:nhanViensList){
         nhanVienDefaultTableModel.addRow(nhanVien.toObjects());
     }
     
     for(PhanCong pc:phanCongsList){
         phanCongDefaultTableModel.addRow(pc.toObjects());
     }
    }

    private void saveDataToFile(JButton btn, ArrayList list, String file) {
       btn.addActionListener((e) -> {
           IOFile.output(list, file);
           JOptionPane.showMessageDialog(null, "Da luu vao"+file);
       });
       
    }

    private void loadDataToBox() {
        maDuAnCB.removeAllItems();
        maNhanVienCB.removeAllItems();
        
        for(DuAn da:duAnsList){
            maDuAnCB.addItem(da.getMaDuAn()+"");
        }
        for(NhanVien nhanVien: nhanViensList){
            maNhanVienCB.addItem(nhanVien.getMaNV()+"");
        }
    }
}

