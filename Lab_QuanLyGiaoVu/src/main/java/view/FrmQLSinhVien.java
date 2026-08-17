/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.SinhVienDao;
import java.awt.BorderLayout;
import java.awt.Label;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.SinhVien;

/**
 *
 * @author PC_TEACHER
 */
public class FrmQLSinhVien extends JFrame  {
    
    private JTextField txtMaSV, txtHoten, txtNgaySinh, txtDiaChi;
    private JRadioButton rdNam, rdNu;
    private JComboBox<String> jboKhoa;
    private JTable tblSinhVien;
    
    private JButton btThem;
    private JButton btXoa;
    private JButton btNapDuLieu;
    
    DefaultTableModel model;
    SinhVienDao svDAO = new SinhVienDao();
    
    int stt=0;
    public FrmQLSinhVien()
    {
       taoGiaoDien();
       //napDuLieuChoJTable();
       xuLySuKien();
    }

    private void taoGiaoDien() {
         
         String tieudecot[] = {"Mã sinh viên","Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ","Mã khoa"};
         model = new DefaultTableModel(tieudecot, 0);
         tblSinhVien = new JTable();
         tblSinhVien.setModel(model);
         JScrollPane scrollTable = new JScrollPane(tblSinhVien);
         
         add(scrollTable, BorderLayout.CENTER);
         
         JPanel pButton = new JPanel();
         pButton.add(btNapDuLieu = new JButton("Hiển thị sinh viên"));         
         pButton.add(btThem= new JButton("Thêm"));
         pButton.add(btXoa = new JButton("Xóa"));
         
         //set hinh cho nut lenh
         btThem.setIcon(new ImageIcon(getClass().getResource("/img/add.png")));
         
         
         add(pButton, BorderLayout.SOUTH);
         
         JPanel pNhapLieu = new JPanel();
         
         pNhapLieu.add(new Label("Mã sinh viên"));
         pNhapLieu.add(txtMaSV = new JTextField(10));
         pNhapLieu.add(new Label("Họ tên sinh viên"));
         pNhapLieu.add(txtHoten = new JTextField(20));
         
         add(pNhapLieu, BorderLayout.NORTH);        
        
    }
    
    public static void main(String[] args) {
         FrmQLSinhVien frm = new FrmQLSinhVien();     
         frm.setSize(600,500);
         frm.setLocationRelativeTo(null);
         frm.setVisible(true);
    }

    private void xuLySuKien() {
         btThem.addActionListener((evt) -> {
           //them 1 dong du lieu vaog JTable
           model.addRow(new Object[]{txtMaSV.getText(),txtHoten.getText(), "2008-01-30","Nam","TPHCM","CNTT"}) ;      
         
         });
         
         btXoa.addActionListener((evt) ->{
            //lay chi so dong duoc chon
            int chiso_dong_chon = tblSinhVien.getSelectedRow();  
            if(chiso_dong_chon<0) //chua chon
            {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa" );
                return;
            }
             model.removeRow(chiso_dong_chon);
         });
         
          btNapDuLieu.addActionListener((evt) ->{             
              napDuLieuChoJTable();             
         });
         
         
    }

    private void napDuLieuChoJTable() {
       
        model.setNumRows(0);
        var ds = svDAO.findAll();       
        for (SinhVien sv : ds) {
           // System.out.print(sv);
           model.addRow(new Object[]{sv.getMaSV(),sv.getHoTen(), sv.getNgaySinh() , sv.isGioiTinh()?"Nam":"Nữ", sv.getDiaChi(), sv.getMaKhoa() }) ;   
        }
    }
    
    
}
