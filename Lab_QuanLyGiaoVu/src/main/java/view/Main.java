/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.SinhVien;
import dao.SinhVienDao;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MSI
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            menu();
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Hiển thị danh sách sinh viên");
                    hienThiDanhSach();
                    break;

                case 2:
                    themSinhVien();
                    break;
                case 3:
                    capNhatSinhVien();
                    break;
                case 4:
                    xoaSinhVien();
                    break;
                case 5:
                    timSinhVienTheoMa();
                    break;
                case 6:
                    timSinhVienTheoTen();
                    break;
                case 0:
                    System.out.println("Kết thúc chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
    }

    public static void menu() {
        System.out.println();
        System.out.println("=========================================");
        System.out.println(" QUẢN LÝ SINH VIÊN");
        System.out.println("=========================================");
        System.out.println("1. Hiển thị danh sách sinh viên");
        System.out.println("2. Thêm sinh viên");
        System.out.println("3. Cập nhật sinh viên");
        System.out.println("4. Xóa sinh viên");
        System.out.println("5. Tìm theo mã");
        System.out.println("6. Tìm theo tên");
        System.out.println("0. Thoát");
        System.out.println("=========================================");
        System.out.print("Nhập lựa chọn: ");
    }

    public static void hienThiDanhSach() {
        SinhVienDao svDAO = new SinhVienDao();
        var ds = svDAO.findAll();
        System.out.println();
        System.out.println("DANH SÁCH SINH VIÊN");
        System.out.println("----------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-12s %-6s %-20s %-10s%n",
                "Mã SV", "Họ tên", "Ngày sinh", "GT", "Địa chỉ", "Khoa");

        for (SinhVien sv : ds) {
            System.out.print(sv);
        }
    }

    public static void themSinhVien() {
        Scanner sc = new Scanner(System.in);
        System.out.println("THÊM SINH VIÊN");

        System.out.print("Mã sinh viên: ");
        String maSV = sc.nextLine();

        System.out.print("Họ tên: ");
        String hoTen = sc.nextLine();

        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        String ngaySinhStr = sc.nextLine();        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date ngaySinh=null;
        try {
            ngaySinh = sdf.parse(ngaySinhStr);
            // java.util.Date ngaySinh = Date.valueOf(ngaySinhStr);
        } catch (ParseException ex) {
            System.out.println("kieu khong hop le");
        }

        System.out.print("Giới tính (1-Nam, 0-Nữ): ");
        boolean gioiTinh = sc.nextInt() == 1;
        sc.nextLine();

        System.out.print("Địa chỉ: ");
        String diaChi = sc.nextLine();

        System.out.print("Mã khoa: ");
        String maKhoa = sc.nextLine();

        SinhVien sv = new SinhVien(maSV, hoTen, ngaySinh, gioiTinh, diaChi, maKhoa);

        SinhVienDao svDAO = new SinhVienDao();
        boolean kq = svDAO.insert(sv);

        if (kq) {
            System.out.println("Thêm sinh viên thành công.");
        } else {
            System.out.println("Không thể thêm sinh viên.");
        }
    }

    private static void xoaSinhVien() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã sinh viên cần xóa: ");
        String maSV = sc.nextLine();

        SinhVienDao svDAO = new SinhVienDao();
        SinhVien sv = svDAO.findById(maSV);

        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên cần xóa");
            return;
        }

        System.out.println();
        System.out.println("Thông tin sinh viên");
        System.out.println("-------------------------");
        System.out.println("Mã SV   : " + sv.getMaSV());
        System.out.println("Họ tên  : " + sv.getHoTen());
        System.out.println("Địa chỉ : " + sv.getDiaChi());

        System.out.print("Bạn có chắc chắn muốn xóa? (Y/N): ");
        String answer = sc.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy thao tác.");
            return;
        }

        boolean kq = svDAO.delete(maSV);
        if (kq) {
            System.out.println();
            System.out.println("Xóa sinh viên thành công!");
        } else {
            System.out.println("Xóa sinh viên thất bại!");
        }
    }

    public static void capNhatSinhVien() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã sinh viên cần cập nhật: ");
        String maSV = sc.nextLine();

        SinhVienDao svDAO = new SinhVienDao();
        SinhVien sv = svDAO.findById(maSV);

        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên.");
            return;
        }

        System.out.println("Thông tin hiện tại:");
        System.out.println("Họ tên    : " + sv.getHoTen());
        System.out.println("Ngày sinh : " + sv.getNgaySinh());
        System.out.println("Giới tính : " + (sv.isGioiTinh() ? "Nam" : "Nữ"));
        System.out.println("Địa chỉ   : " + sv.getDiaChi());
        System.out.println("Mã khoa   : " + sv.getMaKhoa());

        System.out.println("Nhập thông tin mới:");
        System.out.print("Họ tên: ");
        String hoTen = sc.nextLine();

        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        String ngaySinhStr = sc.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date ngaySinh=null;
        try {
            ngaySinh = sdf.parse(ngaySinhStr);
            // java.util.Date ngaySinh = Date.valueOf(ngaySinhStr);
        } catch (ParseException ex) {
            System.out.println("kieu khong hop le");
        }
      //  Date ngaySinh = Date.valueOf(ngaySinhStr);

        System.out.print("Giới tính (1-Nam, 0-Nữ): ");
        boolean gioiTinh = sc.nextInt() == 1;
        sc.nextLine();

        System.out.print("Địa chỉ: ");
        String diaChi = sc.nextLine();

        System.out.print("Mã khoa: ");
        String maKhoa = sc.nextLine();

        SinhVien svUpdated = new SinhVien(maSV, hoTen, ngaySinh, gioiTinh, diaChi, maKhoa);

        boolean kq = svDAO.update(svUpdated);
        if (kq) {
            System.out.println("Cập nhật thành công.");
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }

    public static void timSinhVienTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====TÌM SINH VIÊN THEO MÃ=====");
        System.out.print("Nhập mã sinh viên cần tìm: ");
        String maSV = sc.nextLine();

        SinhVienDao svDAO = new SinhVienDao();
        SinhVien sv = svDAO.findById(maSV);

        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên có mã này!");
        } else {
            System.out.println();
            System.out.println("KẾT QUẢ TÌM KIẾM");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-12s %-6s %-20s %-10s%n",
                    "Mã SV", "Họ tên", "Ngày sinh", "GT", "Địa chỉ", "Khoa");
//            System.out.printf("%-10s %-20s %-12s %-6s %-20s %-10s%n",
//                    sv.getMaSV(),
//                    sv.getHoTen(),
//                    sv.getNgaySinh(),
//                    sv.isGioiTinh() ? "Nam" : "Nữ",
//                    sv.getDiaChi(),
//                    sv.getMaKhoa());
            System.out.print(sv);
        }

    }

    public static void timSinhVienTheoTen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== TÌM SINH VIÊN THEO TÊN =====");
        System.out.print("Nhập tên sinh viên cần tìm: ");
        String timSV = sc.nextLine();

        SinhVienDao svDAO = new SinhVienDao();
        List<SinhVien> ds = svDAO.findByName(timSV);

        if (ds.isEmpty()) {
            System.out.println("Không tìm thấy sinh viên nào phù hợp!");
        } else {
            System.out.println();
            System.out.println("DANH SÁCH SINH VIÊN TÌM THẤY");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-12s %-6s %-20s %-10s%n",
                    "Mã SV", "Họ tên", "Ngày sinh", "GT", "Địa chỉ", "Khoa");
            for (SinhVien sv : ds) {
//                System.out.printf("%-10s %-20s %-12s %-6s %-20s %-10s%n",
//                        sv.getMaSV(),
//                        sv.getHoTen(),
//                        sv.getNgaySinh(),
//                        sv.isGioiTinh() ? "Nam" : "Nữ",
//                        sv.getDiaChi(),
//                        sv.getMaKhoa());
                 System.out.print(sv);
            }
        }

    }
}
