/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import com.google.gson.Gson;
import commonClasses.Action;
import commonClasses.ActionType;
import commonClasses.*;
import commonClasses.LoginActionResponse;
import commonClasses.Menu;
import commonClasses.Order;
import commonClasses.RegisterActionResponse;
import commonClasses.SubmitOrderActionResponse;
import commonClasses.User;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
    public SubmitOrderActionResponse createOrder(ArrayList list){
        
         Order order = new Order(1, 2,list);
        var orderJson =  gson.toJson(order);
       var action = new Action();
        action.setActionType(ActionType.SubmitOrder);
       action.setActionParamsJSON(orderJson);
      var newOrder= gson.toJson(action);
             try {
          
          clientOutput.write(newOrder+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
                   var response = serveInput.readLine();
              
     SubmitOrderActionResponse submitOrderActionResponse = gson.fromJson(response, SubmitOrderActionResponse.class);
      
     return submitOrderActionResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
        return null;
       
         
     }
     public GetMenuResponse getMenu(){
        
        Menu menu = new Menu();
        var orderJson =  gson.toJson(menu);
       var action = new Action();
        action.setActionType(ActionType.GetMenu);
       action.setActionParamsJSON(orderJson);
      var getMenu= gson.toJson(action);
             try {
          
          clientOutput.write(getMenu+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
                   var response = serveInput.readLine();
              
     GetMenuResponse getMenuResponse = gson.fromJson(response, GetMenuResponse.class);
      
     return getMenuResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
        return null;
       
         
     }
     public EditOrderActionResponse editOrder(ArrayList list){
        
         Order order = new Order(1, 2,list);
        var editOrderJson =  gson.toJson(order);
       var action = new Action();
        action.setActionType(ActionType.EditOrder);
       action.setActionParamsJSON(editOrderJson);
      var editOrder= gson.toJson(action);
             try {
          
          clientOutput.write(editOrder+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
                   var response = serveInput.readLine();
              
     EditOrderActionResponse editOrderActionResponse = gson.fromJson(response, EditOrderActionResponse.class);
      
     return editOrderActionResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
        return null;
       
         
     }
     
      public EditMenuActionResponse editMenu (){
        
         Menu menu = new Menu();
        var editMenuJson =  gson.toJson(menu);
       var action = new Action();
        action.setActionType(ActionType.EditMenu);
       action.setActionParamsJSON(editMenuJson);
      var editMenu= gson.toJson(action);
             try {
          
          clientOutput.write(editMenu+System.lineSeparator());
          clientOutput.flush();
          while(true){
              if(serveInput.ready()){
                  
                   var response = serveInput.readLine();
              
     EditMenuActionResponse editMenuActionResponse = gson.fromJson(response, EditMenuActionResponse.class);
      
     return editMenuActionResponse;
              }
          } 
     
      } catch (IOException ex) {
          ex.printStackTrace();
      }
        return null;
       
         
     }
    }
  


