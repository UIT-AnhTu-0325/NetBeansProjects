/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qltt.client;

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

        get("/categories", CategoryController::getList);
        get("/categories/:category", CategoryController::getCategory);
        post("/categories/store", CategoryController::saveCategory);
        put("/categories/:category/update", CategoryController::updateCategory);
        delete("/categories/:category/delete", CategoryController::deleteCategory);
        
        get("/newss", NewsController::getList);
        get("/newss/:news", NewsController::getNews);
        post("/newss/store", NewsController::saveNews);
        put("/newss/:news/update", NewsController::updateNews);
        delete("/newss/:news/delete", NewsController::deleteNews);
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
