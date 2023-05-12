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

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int MaLoai;
    private String TenLoai;
    private String MoTa;

    public Category() {
    }

    public Category(int MaLoai, String TenLoai, String MoTa) {
        this.MaLoai = MaLoai;
        this.TenLoai = TenLoai;
        this.MoTa = MoTa;
    }

    @Override
    public String toString() {
        return "Category{" + "MaLoai=" + MaLoai + ", TenLoai=" + TenLoai + ", MoTa=" + MoTa + '}';
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int MaLoai) {
        this.MaLoai = MaLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String TenLoai) {
        this.TenLoai = TenLoai;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
}

//    public String getId() {
//        return MaLoai;
//    }
//
//    public void setId(String id) {
//        this.MaLoai = MaLoai;
//    }
//
//    public String getName() {
//        return TenLoai;
//    }
//
//    public void setName(String name) {
//        this.TenLoai = TenLoai;
//    }
//
//    public String getDescription() {
//        return Mota;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @Override
//    public String toString() {
//        return "Floor{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
//    }
//}
