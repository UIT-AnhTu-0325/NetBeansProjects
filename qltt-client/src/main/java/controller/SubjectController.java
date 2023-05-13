/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Subject;
import com.google.gson.Gson;
import model.StudentGrade;
import spark.Request;
import spark.Response;

/**
 *
 * @author ADMIN
 */
//public class SubjectController {
//    
//}

public class SubjectController {
    private static SubjectController instance;
    
    private final Socket client;
    private final DataInputStream dis; //nhận 1 chuỗi
    private final DataOutputStream dos; //truyền 1 chuỗi
    private final ObjectOutputStream oos; // truyền 1 đối tượng
    private final ObjectInputStream ois; // nhận 1 đối tượng
    
    private final String IP = "localhost"; //địa chỉ server socket
    private final int PORT_NUMBER = 7890; //port server

    
    public static SubjectController getInstance() throws IOException {
        if (instance == null) {
            instance = new SubjectController();
        }
        return instance;
    }
    
    public SubjectController() throws IOException {
        client = new Socket(IP, PORT_NUMBER); //khởi tạo kết nối với client
        System.out.println(client + " client " + client.getLocalPort());
        dis = new DataInputStream(client.getInputStream()); //luồng nhận của client
        dos = new DataOutputStream(client.getOutputStream());// luồng truyền của client
        oos = new ObjectOutputStream(client.getOutputStream());
        ois = new ObjectInputStream(client.getInputStream());
    }
    
    public static String getList(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
//        String key = req.queryParamOrDefault("key", "");
        fc.dos.writeUTF("subjects" );
        fc.dos.flush();
        
        Subject[] subjects = null;
        try {
            subjects = (Subject[]) fc.ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("subjects", subjects);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
    public static String getSubject(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String MaMH = req.params("subject");
        fc.dos.writeUTF("subjects/" + MaMH);
        fc.dos.flush();
        
        StudentGrade[] studentGrades = null;
        try {
            studentGrades = (StudentGrade[]) fc.ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("studentGrades", studentGrades);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
    public static String saveSubject(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String body = req.body();
        Subject subject = new Gson().fromJson(body, Subject.class);
        
        fc.dos.writeUTF("subjects/store");
        fc.dos.flush();
        fc.oos.writeObject(subject);
        fc.oos.flush();
        
        String status = (String) fc.dis.readUTF();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
    public static String updateSubject(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String MMH = req.params("subject");
        int MaMH = Integer.parseInt(MMH);
        String body = req.body();
        Subject subject = new Gson().fromJson(body, Subject.class);
        subject.setMaMH(MaMH);
        
        fc.dos.writeUTF("subjects/update");
        fc.dos.flush();
        fc.oos.writeObject(subject);
        fc.oos.flush();
        
        String status = (String) fc.dis.readUTF();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
    public static String deleteSubject(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String MaMH = req.params("subject");
        
        fc.dos.writeUTF("subjects/delete");
        fc.dos.flush();
        fc.dos.writeUTF(MaMH);
        fc.dos.flush();
        
        String status = (String) fc.dis.readUTF();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
    public static String saveGrade(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String body = req.body();
        StudentGrade grade = new Gson().fromJson(body, StudentGrade.class);
        
        fc.dos.writeUTF("subjects/gradestore");
        fc.dos.flush();
        fc.oos.writeObject(grade);
        fc.oos.flush();
        
        String status = (String) fc.dis.readUTF();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
    
        public static String deleteGrade(spark.Request req, spark.Response res) throws IOException {
        SubjectController fc = getInstance();
        String body = req.body();
        StudentGrade grade = new Gson().fromJson(body, StudentGrade.class);
        
        fc.dos.writeUTF("subjects/gradedelete");
        fc.dos.flush();
        fc.oos.writeObject(grade);
        fc.oos.flush();
        
        String status = (String) fc.dis.readUTF();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
}
