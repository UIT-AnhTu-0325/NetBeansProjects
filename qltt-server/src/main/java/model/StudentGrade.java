/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class StudentGrade implements Serializable {
    public StudentGrade (){
        
    }
    private static final long serialVersionUID = 1L;
    private int MASV ;
    private int MAMH ;
    private double DIEMCC ;
    private double DIEMBTL ;
    private double DIEMCK ;
    private String TENSV;

    public void setTENSV(String TENSV) {
        this.TENSV = TENSV;
    }

    public String getTENSV() {
        return TENSV;
    }
    
    public int getMASV() {
        return MASV;
    }

    public void setMASV(int MASV) {
        this.MASV = MASV;
    }

    public void setMAMH(int MAMH) {
        this.MAMH = MAMH;
    }

    public void setDIEMCC(double DIEMCC) {
        this.DIEMCC = DIEMCC;
    }

    public void setDIEMBTL(double DIEMBTL) {
        this.DIEMBTL = DIEMBTL;
    }

    public void setDIEMCK(double DIEMCK) {
        this.DIEMCK = DIEMCK;
    }

    public int getMAMH() {
        return MAMH;
    }

    public double getDIEMCC() {
        return DIEMCC;
    }

    public double getDIEMBTL() {
        return DIEMBTL;
    }

    public double getDIEMCK() {
        return DIEMCK;
    }    
}
