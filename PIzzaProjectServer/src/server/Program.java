/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author PC
 */
public class Program {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        while(true)
        {
            System.out.println("Type 'exit' to exit");
              BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
 
        // Reading data using readLine
        String read = reader.readLine();
        if(read.equals("exit")){
            server.stop();
            return;
        }
        }
    }
}
