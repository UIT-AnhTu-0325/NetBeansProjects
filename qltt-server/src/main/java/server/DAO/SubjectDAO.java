/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Subject;

/**
 *
 * @author ADMIN
 */
//public class SubjectDAO {
//    
//}

public class SubjectDAO {
    private Connection connection;
    private static SubjectDAO instance;

    public static SubjectDAO getInstance(Connection connection) throws IOException {
        if (instance == null) {
            instance = new SubjectDAO(connection);
        }
        return instance;
    }
    
    public SubjectDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Subject> getAllSubject() throws SQLException, IOException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects";
//        if (!"".equals(key)) {
//            sql += " where name LIKE '%" + key + "%'";
//        }
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Subject subject = new Subject(result.getInt("MaMH"), result.getString("TenMH"), result.getString("STC"));
            subjects.add(subject);
        }
        return subjects;
    }

    public Subject getSubjectById(int MaMH) throws SQLException {
        String sql = "SELECT * FROM subjects WHERE MaMH = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, MaMH);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            Subject subject = new Subject(result.getInt("MaMH"), result.getString("TenMH"), result.getString("STC"));
            return subject;
        } else {
            return null;
        }
    }

    public void addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects (MaMH, TenMH, STC) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, subject.getMaMH());
        statement.setString(2, subject.getTenMH());
        statement.setString(3, subject.getSTC());
        statement.executeUpdate();
    }

    public void updateSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subjects SET TenMH = ?, STC = ? WHERE MaMH = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, subject.getTenMH());
        statement.setString(2, subject.getSTC());
        statement.setInt(3, subject.getMaMH());
        statement.executeUpdate();
    }

    public void deleteSubject(int MaMH) throws SQLException {
        String sql = "DELETE FROM subjects WHERE MaMH = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, MaMH);
        statement.executeUpdate();
    }

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects";
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
            Subject subject = new Subject(result.getInt("MaMH"), result.getString("TenMH"),result.getString("STC"));
            subjects.add(subject);
        }
        return subjects;
    }
}

