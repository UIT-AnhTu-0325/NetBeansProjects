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
import model.Student;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 *
 * @author ADMIN
 */
//public class StudentController {
//    
//}

public class StudentController {
    private static StudentController instance;

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private final String IP = "localhost";
    private final int PORT_NUMBER = 7890;

    public static StudentController getInstance() throws IOException {
        if (instance == null) {
            instance = new StudentController();
        }
        return instance;
    }

    public StudentController() throws IOException {
        client = new Socket(IP, PORT_NUMBER);
        System.out.println(client + " client " + client.getLocalPort());
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        oos = new ObjectOutputStream(client.getOutputStream());
        ois = new ObjectInputStream(client.getInputStream());
    }

    public static String getList(Request req, Response res) throws IOException {
        StudentController rc = getInstance();
//        String key = req.queryParamOrDefault("key", "");
//        String floorId = req.queryParamOrDefault("floor_id", "");
//        String numberPeople = req.queryParamOrDefault("numberPeople", "");
        rc.dos.writeUTF("students?");
        rc.dos.flush();

        Student[] students = null;
        try {
            students = (Student[]) rc.ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("students", students);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }

    public static String getStudent(Request req, Response res) throws IOException {
        StudentController rc = getInstance();
        String MaSV = req.params("student");
        rc.dos.writeUTF("students/" + MaSV);
        rc.dos.flush();

        Student student = null;
        try {
            student = (Student) rc.ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("student", student);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }

    public static String saveStudent(Request req, Response res) throws IOException {
        StudentController rc = getInstance();
        String body = req.body();
        Student student = new Gson().fromJson(body, Student.class);

        rc.dos.writeUTF("students/store");
        rc.dos.flush();
        rc.oos.writeObject(student);
        rc.oos.flush();

        String status = (String) rc.dis.readUTF();

        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        Gson gson = new Gson();
        String json = gson.toJson(response);

        // Set the response type to "application/json"
        res.type("application/json");

        // Return the JSON string as the response body
        return json;
    }
 
    public static String updateStudent(spark.Request req, spark.Response res) throws IOException {
        StudentController fc = getInstance();
        String MSV = req.params("student");
        int MaSV = Integer.parseInt(MSV);
        String body = req.body();
        Student student = new Gson().fromJson(body, Student.class);
        student.setMaSV(MaSV);

        fc.dos.writeUTF("students/update");
        fc.dos.flush();
        fc.oos.writeObject(student);
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

    public static String deleteStudent(spark.Request req, spark.Response res) throws IOException {
        StudentController fc = getInstance();
        String MaSV = req.params("student");

        fc.dos.writeUTF("students/delete");
        fc.dos.flush();
        fc.dos.writeUTF(MaSV);
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

}
