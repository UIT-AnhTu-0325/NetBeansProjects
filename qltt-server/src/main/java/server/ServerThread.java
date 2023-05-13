package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.DAO.SubjectDAO;
import model.Subject;
import model.Student;
import model.StudentGrade;
import server.DAO.StudentDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private DataInputStream dis;  //nhận string từ client
    private DataOutputStream dos;
    private ObjectInputStream ois; //nhận obj từ client
    private ObjectOutputStream oos;

//	khởi tạo
    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }
    

    @Override
    public void run() {
        while (true) {
            try {
                String message = dis.readUTF();
                System.out.println("Request: " + message);
                
                if (message.contains("subjects/store")) {
                    
                    Subject subject = (Subject) ois.readObject();
                    saveSubject(subject);
                }else if (message.contains("subjects/gradestore")){
                    
                    StudentGrade grade = (StudentGrade) ois.readObject();
                    saveGrade(grade);
                }else if (message.contains("subjects/gradedelete")){
                    StudentGrade grade = (StudentGrade) ois.readObject();
                    deleteGrade(grade);
                } else if (message.contains("subjects/update")) {
                    
                    Subject subject = (Subject) ois.readObject();
                    updateSubject(subject);
                } else if (message.contains("subjects/delete")) {
                    
                    String MMH = dis.readUTF();
                    int MaMH = Integer.parseInt(MMH);
                    deleteSubject(MaMH);
                } else if (message.contains("subjects/")) {
                    
                    String[] strs = message.split("/");
                    String MMH = strs.length > 1 ? strs[1] : "";
                    int MaMH = Integer.parseInt(MMH);
                    getSubject(MaMH);
                } else if (message.contains("subjects")) {
                    
//                    String[] strs = message.split("key=");
//                    String key = strs.length > 1 ? strs[1] : "";
                    getListSubjects();
                } 
                else 
                if (message.contains("students/store")) {
                    Student student = (Student) ois.readObject();
                    saveStudent(student);
                } else if (message.contains("students/update")) {
                    Student student = (Student) ois.readObject();
                    updateStudent(student);
                } else if (message.contains("students/delete")) {
                    String MSV = dis.readUTF();
                    int MaSV = Integer.parseInt(MSV);
                    deleteStudent(MaSV);
                } else if (message.contains("students/")) {
                    String[] strs = message.split("/");
                    String MSV = strs.length > 1 ? strs[1] : "";
                    int MaSV = Integer.parseInt(MSV);
                    getStudent(MaSV);
                } else if (message.contains("students")) {
//                    String key = getStringBetween(message, "key=", "&");
//                    String TenLoai = getStringBetween(message, "TenLoai=", "&");
//                    int numberPeople;
//                    try {
//                        numberPeople = Integer.parseInt(getStringBetween(message, "numberPeople=", ""));
//                    } catch (NumberFormatException e2) {
//                        System.err.println(e2);
//                        numberPeople = 0;
//                    }
                    getListStudent();
                }
                
            } catch (Exception e) {
                System.err.println(e);
                //closeConnection();
                return;
            }
        }
    }
    
    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connection is closed!");
    }
    
    
    private void getListSubjects() throws IOException, SQLException {
        List<Subject> list = null;
        list = SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).getAllSubjects();
        Subject[] subjects = null;
        if (list != null) {
            subjects = list.toArray(new Subject[list.size()]);
        }
        oos.writeObject(subjects);
        oos.flush();
    }
    
    private void getSubject(int MaMH) throws IOException {
        try {
            List<StudentGrade> list = null;
            list = SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).getSubjectById(MaMH);
            StudentGrade[] studentGrades = null;
            if (list != null) {
                studentGrades = list.toArray(new StudentGrade[list.size()]);
            }
            oos.writeObject(studentGrades);
            oos.flush();
        } catch (Exception e) {
            System.err.println(e);
            oos.writeObject(null);
            oos.flush();
        }
    }
    
    private void saveSubject(Subject subject) throws IOException, SQLException {
        try {
            SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).addSubject(subject);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void saveGrade(StudentGrade grade) throws IOException, SQLException {
        try {
            SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).addGrade(grade);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void deleteGrade(StudentGrade grade) throws IOException, SQLException {
        try {
            SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).deleteGrade(grade);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void updateSubject(Subject subject) throws IOException, SQLException {
        try {
            SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).updateSubject(subject);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private void deleteSubject(int MaMH) throws IOException, SQLException {
        try {
            SubjectDAO.getInstance(Controller.getInstance().getDBConnection()).deleteSubject(MaMH);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    
    private void getListStudent() throws IOException {
        try {
            List<Student> list = StudentDAO.getInstance(Controller.getInstance().getDBConnection()).getAllStudent();
            Student[] students = list.toArray(new Student[list.size()]);
            oos.writeObject(students);
            oos.flush();
        } catch (Exception e) {
            System.err.println(e);
            oos.writeObject(null);
            oos.flush();
        }
    }

    private void getStudent(int MaSV) throws IOException {
        try {
            Student student = StudentDAO.getInstance(Controller.getInstance().getDBConnection()).getStudentById(MaSV);
            oos.writeObject(student);
            oos.flush();
        } catch (Exception e) {
            System.err.println(e);
            oos.writeObject(null);
            oos.flush();
        }
    }

    private void saveStudent(Student student) throws IOException, SQLException {
        try {
            StudentDAO.getInstance(Controller.getInstance().getDBConnection()).addStudent(student);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }

    private void updateStudent(Student student) throws IOException, SQLException {
        try {
            StudentDAO.getInstance(Controller.getInstance().getDBConnection()).updateStudent(student);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }

    private void deleteStudent(int MaSV) throws IOException, SQLException {
        try {
            StudentDAO.getInstance(Controller.getInstance().getDBConnection()).deleteStudent(MaSV);
            dos.writeUTF("success!");
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            dos.writeUTF(ex.toString());
            dos.flush();
        }
    }
    
    private String getStringBetween(String str, String start, String end) {
        String[] strs = str.split(start);
        if (strs.length == 1) {
            return "";
        }
        if ("".equals(end)) {
            return strs[1];
        }
        return strs[1].split(end)[0];
    }
}
