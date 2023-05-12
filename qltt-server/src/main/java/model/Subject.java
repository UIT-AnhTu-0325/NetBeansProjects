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
//public class News {
//    
//}

import java.io.Serializable;

/**
 *
 * @author Dduwcs
 */

/*
create table floors (
	id varchar(50),
	name varchar(255),
	description varchar(255),
	Primary Key (id) 
);
*/

public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int MaMH;
    private String TenMH;
    private String STC;

    public Subject() {
    }

    public Subject(int MaMH, String TenMH, String STC) {
        this.MaMH = MaMH;
        this.TenMH = TenMH;
        this.STC = STC;
    }

    public int getMaMH() {
        return MaMH;
    }

    public void setMaMH(int MaMH) {
        this.MaMH = MaMH;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String TenMH) {
        this.TenMH = TenMH;
    }

    public String getSTC() {
        return STC;
    }

    public void setSTC(String STC) {
        this.STC = STC;
    }
    
    

    @Override
    public String toString() {
        return "Subject{" + "MaMH=" + MaMH + ", TenMH=" + TenMH + ", STC=" + STC + '}';
    }

    
}
