/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Khoa;
import util.DBConection;

/**
 *
 * @author PC_TEACHER
 */
public class KhoaDAO {
    
     public List<Khoa> findAll() {
        List<Khoa> ds = new ArrayList<>();
        String sql = "SELECT * FROM KHOA";
        
        try (Connection conn = DBConection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String makhoa = rs.getString("MaKhoa");
                String tenKhoa = rs.getString("TenKhoa");                
                
                ds.add(new Khoa(makhoa, tenKhoa));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
        return ds;
    }

    public boolean delete(String maKhoa) {
        String sql = "DELETE FROM KHOA WHERE MaKhoa=?";
        
        try (Connection conn = DBConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maKhoa);
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa dữ liệu: " + e.getMessage());
        }
        return false;
    }

    public Khoa findById(String maKhoa) {
        String sql = "SELECT * FROM KHOA WHERE MaKhoa=?";
        
        try (Connection conn = DBConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maKhoa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Khoa(rs.getString("makhoa"), rs.getString("TenKhoa"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Khoa kh) {
        String sql = "INSERT INTO KHOA VALUES (?, ?)";
        
        try (Connection conn = DBConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kh.getMaKhoa());
            ps.setString(2, kh.getTenKhoa());           
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm dữ liệu: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Khoa kh) {
        String sql = "UPDATE KHOA SET TenKhoa = ? WHERE MaKhoa = ?";
        
        
        try (Connection conn = DBConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kh.getTenKhoa());            
            ps.setString(2, kh.getMaKhoa());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật dữ liệu: " + e.getMessage());
        }
        return false;
    }        
 
    //test 
    public static void main(String[] args) {
        
        KhoaDAO  khDAO = new KhoaDAO();
         
         khDAO.insert(new Khoa("KTXD","Kỹ thuật xây dựng"));
        
        //lay tat cac khoa va hien thi
        for(Khoa x : khDAO.findAll())
        {
            System.out.println(x);
        }        
        
    }
}
