/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author ADMIN
 */

import static spark.Spark.*;
import com.google.gson.Gson;
import java.io.IOException;

public class Main {
    private final static int PORT_NUMBER = 8901;
    
    public static void main(String[] args) throws IOException {
        port(PORT_NUMBER);

        options("/*",
        (request, response) -> {

            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });
        
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
            
        get("/", (req, res) -> {
            MyObject obj = new MyObject();
            obj.setMessage("Hello, world!");
            Gson gson = new Gson();
            return gson.toJson(obj);
        });

        get("/subjects", SubjectController::getList);
        get("/subjects/:subject", SubjectController::getSubject);
        post("/subjects/store", SubjectController::saveSubject);
        put("/subjects/:subject/update", SubjectController::updateSubject);
        delete("/subjects/:subject/delete", SubjectController::deleteSubject);
        
        get("/students", StudentController::getList);
        get("/students/:student", StudentController::getStudent);
        post("/students/store", StudentController::saveStudent);
        put("/students/:student/update", StudentController::updateStudent);
        delete("/students/:student/delete", StudentController::deleteStudent);
    }
}

class MyObject {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
