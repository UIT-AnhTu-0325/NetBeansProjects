/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;

/**
 *
 * @author Dduwcs
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Controller.getInstance().start();
    }
}