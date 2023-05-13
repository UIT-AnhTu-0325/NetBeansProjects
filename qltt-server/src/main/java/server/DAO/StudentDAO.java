/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Student;

/**
 *
 * @author ADMIN
 */
//public class StudentDAO {
//    
//}

public class StudentDAO {
    private Connection connection;
    private static StudentDAO instance;

    public static StudentDAO getInstance(Connection connection) throws IOException {
        if (instance == null) {
            instance = new StudentDAO(connection);
        }
        return instance;
    }

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Student> getAllStudent() throws SQLException, IOException {
        List<Student> students = new ArrayList<>();
//        System.out.println("getAllStudent: " + key + ", " + Maloai);
        String sql = "SELECT * FROM student";
//        if (!"".equals(key)) {
//            sql += " where title LIKE '%" + key + "%' or content LIKE '%" + key + "%'";
//        }
//        if (Maloai > 0) {
//            if (!"".equals(key)) {
//                sql += " and";
//            } else {
//                sql += " where";
//            }
//            sql += " MaSV = " + Maloai;
//        }
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Student student = new Student(result.getInt("MaSV"), result.getString("TenSV"),result.getString("QueQuan"), result.getDate("NgaySinh"));
            students.add(student);
        }
        return students;
    }

    public Student getStudentById(int MaSV) throws SQLException {
        String sql = "SELECT * FROM student WHERE MaSV = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, MaSV);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            Student student = new Student(result.getInt("MaSV"), result.getString("TenSV"),result.getString("QueQuan"), result.getDate("NgaySinh"));
            return student;
        } else {
            return null;
        }
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (MaSV, TenSV, QueQuan, NgaySinh) VALUES (?, ?, ?, ?)";
//        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, student.getMaSV());
            statement.setString(2, student.getTenSV());
            statement.setString(3, student.getQueQuan());
            statement.setDate(4, new Date(student.getNgaySinh().getTime()));

            statement.executeUpdate();
//            return true; // thanh cong!
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//            return false;
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET TenSV = ?, QueQuan = ?, NgaySinh = ? WHERE MaSV = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, student.getMaSV());
        statement.setString(2, student.getTenSV());
        statement.setString(3, student.getQueQuan());
        statement.setDate(4, new Date(student.getNgaySinh().getTime())); //ps.setDate(3, new Date(s.getDob().getTime()));
        statement.executeUpdate();
    }

    public void deleteStudent(int MaSV) throws SQLException {
        String sql = "DELETE FROM student WHERE MaSV = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, MaSV);
        statement.executeUpdate();
    }
}

