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

    public Student(int MaSV, String TenSV, String QueQuan, Date dob) {
        this.MaSV = MaSV;
        this.TenSV = TenSV;
        this.QueQuan = QueQuan;
        this.NgaySinh = NgaySinh;
    }

    

    @Override
    public String toString() {
        return "News{" + "MaSV=" + MaSV + ", TenSV=" + TenSV + ", QueQuan=" + QueQuan + ", NgaySinh=" + NgaySinh + '}';
    }

    public void setMaSV(int MaSV) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}

