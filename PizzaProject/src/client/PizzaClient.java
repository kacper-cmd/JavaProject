/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import com.google.gson.Gson;
import commonClasses.Action;
import commonClasses.ActionType;
import commonClasses.LoginActionResponse;
import commonClasses.RegisterActionResponse;
import commonClasses.User;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class PizzaClient {
  public Socket socket;
  private BufferedReader serveInput; 
  private BufferedWriter clientOutput;
Gson gson = new Gson();
    public PizzaClient(String ip, int port){
      try {
          socket = new Socket(ip,port);
          serveInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          clientOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
          
      } catch (IOException ex) {
ex.printStackTrace();      }
      }
      
    public LoginActionResponse Login(User user){
     var userJson =  gson.toJson(user);
     var action = new Action();
     action.setActionType(ActionType.Login);
     action.setActionParamsJSON(userJson);
     var payload= gson.toJson(action);
     
     
      try {
          
          clientOutput.write(payload+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
              
                   var response = serveInput.readLine();
              
     LoginActionResponse actionResponse = gson.fromJson(response, LoginActionResponse.class);
      
     return actionResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
return null;
    }
    public RegisterActionResponse Register(User user){
         var userJson =  gson.toJson(user);
     var action = new Action();
     action.setActionType(ActionType.Register);
     action.setActionParamsJSON(userJson);
     var payload= gson.toJson(action);
     
      try {
          
          clientOutput.write(payload+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
              
                   var response = serveInput.readLine();
              
     RegisterActionResponse actionResponse = gson.fromJson(response, RegisterActionResponse.class);
      
     return actionResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
return null;
     
    }
    }
  


