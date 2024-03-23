package project_java_group_5;

import java.util.Calendar;

public abstract class NhanVien {
    // properties:

    private String ten;

    private String quocTich;

    private String ngaySinh;

    private Integer thamNien;

    private String vaitro;

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getVaitro() {
        return vaitro;
    }

    // constructor:
    public NhanVien() {
    }

    public NhanVien(String ten, String quocTich, String ngaySinh, Integer thamNien, String vaitro) {
        this.ten = ten;
        this.quocTich = quocTich;

        this.ngaySinh = ngaySinh;

        this.thamNien = thamNien;
        this.vaitro = vaitro;
    }

    // get and set:
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setThamNien(Integer thamNien) {
        this.thamNien = thamNien;
    }

    public Integer getThamNien() {
        return thamNien;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getTuoi(String ngaySinh) {
        //Lay nam hien tai
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);

        //Lay nam Nhan Vien sinh ra
        String[] parts = ngaySinh.trim().split("\\s*/\\s*");
        int year1 = Integer.parseInt(parts[2]); //Lay nam sinh

        return year1 - year;
    }

    public static int hesoLuong(Integer thamNien) {

        if (thamNien >= 0 && thamNien < 10) { //Duoi 10 nam
            return 1;
        } else if (thamNien >= 10 && thamNien < 20) {
            return 2;
        }
        return 3;
    }

    // @req: Tinh luong:
    
    abstract Integer tinhLuong();

    abstract Integer tinhThuong();
}
