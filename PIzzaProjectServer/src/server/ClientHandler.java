/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import commonClasses.Action;
import commonClasses.ActionType;
import commonClasses.EditMenuActionResponse;
import commonClasses.EditOrderActionResponse;
import commonClasses.GetMenuResponse;
import commonClasses.GetOrdersActionResponse;
import commonClasses.LoginActionResponse;
import commonClasses.Menu;
import commonClasses.Order;
import commonClasses.Rank;
import commonClasses.RegisterActionResponse;
import commonClasses.SubmitOrderActionResponse;
import commonClasses.User;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
class ClientHandler extends Thread 
{
   
    final BufferedReader clientInput;
    final BufferedWriter serverOutput;
    final Socket s;
    final Server server;
     User connectedUser;
     boolean exit;
     Gson gson = new Gson();
  
    // Constructor
    public ClientHandler(Socket s, BufferedReader input, BufferedWriter output, Server server) 
    {
        this.s = s;
        this.clientInput = input;
        this.serverOutput = output;
        this.server = server;
    }
    
  
    @Override
    public void run() 
    {
        String received;
        
        while (!exit) 
        {
            try {
             
  received= clientInput.readLine();
                System.out.println("Processing client data "+received);
                Action action = gson.fromJson(received, new TypeToken<Action>(){}.getType());
                ActionType actionType = action.getActionType();
                switch (actionType) {
                    case Login:
                        RespondToLogin(action);
                        break;
                    case Logout:
                        
                        clientInput.close();
                        serverOutput.close();
                        s.close();
                        exit = true;
                        break;
                    case GetMenu:
                        if(connectedUser == null)
                        {
                            GetMenuResponse resp = new GetMenuResponse(server.menu);
                            resp.setIsSuccess(false);
                            sendToClient(resp);
                        }
                        GetMenuResponse resp = new GetMenuResponse(server.menu);
                        resp.setIsSuccess(true);
                        sendToClient(resp);
                    
                       break;
                    case Register:
                    {
                      User user=  gson.fromJson(action.getActionParamsJSON(),new TypeToken<User>(){}.getType());
                      var result = server.addUser(user);
                      RegisterActionResponse rar = new RegisterActionResponse();
                      rar.setSuccess(result);
                        sendToClient(rar);
                          break;
                    }
                    case SubmitOrder:
                    {
                        SubmitOrderActionResponse submitOrderActionResponse = new SubmitOrderActionResponse();
                        if(connectedUser == null){
                            
                            submitOrderActionResponse.setSuccess(false);
                            sendToClient(submitOrderActionResponse);
                             break;
                        }
                        
                        Order order=  gson.fromJson(action.getActionParamsJSON(),new TypeToken<Order>(){}.getType());
                        server.addOrder(order, connectedUser);
                        submitOrderActionResponse.setSuccess(true);
                        sendToClient(submitOrderActionResponse);
                       break;
                    }
                     case GetOrders:
                    {
                        GetOrdersActionResponse getOrdersActionResponse = new GetOrdersActionResponse();
                        if(connectedUser == null){
                            
                            getOrdersActionResponse.setSuccess(false);
                            sendToClient(getOrdersActionResponse);
                             break;
                        }
                        
                       
                        getOrdersActionResponse.setSuccess(true);
                        getOrdersActionResponse.setOrders(server.orders);
                        sendToClient(getOrdersActionResponse);
                       break;
                    }

                        case EditMenu:{
                            EditMenuActionResponse editMenuActionResponse = new EditMenuActionResponse();
                            if(connectedUser == null || connectedUser.getRank()!= Rank.Admin){
                                
                                
                                editMenuActionResponse.setSuccess(false);
                                sendToClient(editMenuActionResponse);
                                break;
                            }
                             Menu menu=  gson.fromJson(action.getActionParamsJSON(),new TypeToken<Menu>(){}.getType());
                             server.setMenu(menu);
                             editMenuActionResponse.setSuccess(true);
                              sendToClient(editMenuActionResponse);
                            break;
                        }
                            case EditOrder:{
                                EditOrderActionResponse editOrderActionResponse = new EditOrderActionResponse();
                                 if(connectedUser == null ){
         
                                editOrderActionResponse.setSuccess(false);
                                sendToClient(editOrderActionResponse);
                                break;
                                                         
                            }
                                 Order order=  gson.fromJson(action.getActionParamsJSON(),new TypeToken<Order>(){}.getType());
                                var result = server.editOrder(order);
                                 editOrderActionResponse.setSuccess(result);
                                 sendToClient(editOrderActionResponse);
                                 break;
                            }

                        
                    default:
                        throw new AssertionError();
                }
             
                
            } catch (IOException e) {
            }
        }
        System.out.println("CLIENT EXITING");
          
    
       }

    private void RespondToLogin(Action action) {
        LoginActionResponse actionResponse = new LoginActionResponse();
        User user =gson.fromJson(action.getActionParamsJSON(), User.class);
     User foundUser =  server.users.stream().filter(u->u.getUserName().equals(user.getUserName())).findFirst().orElse(null);
     
     if(foundUser == null){
            actionResponse.setIsSuccess(false);
         sendToClient(actionResponse);  
         return;
        }
      if(user.getPasswordHash().equals(foundUser.getPasswordHash()))
      {
          actionResponse.setIsSuccess(true);
          actionResponse.setUserId(foundUser.getUserId());
          actionResponse.setRank(foundUser.getRank());
          sendToClient(actionResponse);
          connectedUser = foundUser;
          return;
      }
      actionResponse.setIsSuccess(false);

          sendToClient(actionResponse);
       
    }

    private void sendToClient(LoginActionResponse actionResponse) {
        String data = gson.toJson(actionResponse);
        System.out.println("Sending data to client "+data);
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();  
        }
        

    }

    private void sendToClient(GetMenuResponse resp) {
          String data = gson.toJson(resp);
               System.out.println("Sending data to client "+data);
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
           ex.printStackTrace();  
        }
    }

    private void sendToClient(RegisterActionResponse rar) {
         String data = gson.toJson(rar);
              System.out.println("Sending data to client "+data);
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();  
        }
    }

    private void sendToClient(SubmitOrderActionResponse submitOrderActionResponse) {
          String data = gson.toJson(submitOrderActionResponse);
               System.out.println("Sending data to client "+data);
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
           ex.printStackTrace();  
        }
    }

    private void sendToClient(EditMenuActionResponse editMenuActionResponse) {
       String data = gson.toJson(editMenuActionResponse);
            System.out.println("Sending data to client "+data);
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
            ex.printStackTrace();  
        }
    }

    private void sendToClient(EditOrderActionResponse editOrderActionResponse) {
        String data = gson.toJson(editOrderActionResponse);
             System.out.println("Sending data to client "+data);
        
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
ex.printStackTrace();        }
    }

    private void sendToClient(GetOrdersActionResponse ordersActionResponse) {
          String data = gson.toJson(ordersActionResponse);
             System.out.println("Sending data to client "+data);
        
        try {
            serverOutput.write(data+System.lineSeparator());
            serverOutput.flush();
        } catch (IOException ex) {
ex.printStackTrace();        }
    }
}