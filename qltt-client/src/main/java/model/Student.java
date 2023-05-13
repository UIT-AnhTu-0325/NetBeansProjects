/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
//public class Category {
//    
//}

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int MaSV;
    private String TenSV;
    private String QueQuan;
    private Date NgaySinh;

    public Student() {
    }

    public Student(int MaSV, String TenSV, String QueQuan, Date NgaySinh) {
        this.MaSV = MaSV;
        this.TenSV = TenSV;
        this.QueQuan = QueQuan;
        this.NgaySinh = NgaySinh;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String TenSV) {
        this.TenSV = TenSV;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    

    @Override
    public String toString() {
        return "News{" + "MaSV=" + MaSV + ", TenSV=" + TenSV + ", QueQuan=" + QueQuan + ", NgaySinh=" + NgaySinh +'}';
    }

    public void setMaSV(int MaSV) {
        this.MaSV = MaSV;
    }

    public int getMaSV() {
        return MaSV;
    }

    
}

