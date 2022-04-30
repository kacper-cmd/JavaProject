/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import commonClasses.Action;
import commonClasses.ActionType;
import commonClasses.LoginActionResponse;
import commonClasses.User;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
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
     Gson gson =new Gson();
  
    // Constructor
    public ClientHandler(Socket s, BufferedReader dis, BufferedWriter dos, Server server) 
    {
        this.s = s;
        this.clientInput = dis;
        this.serverOutput = dos;
        this.server = server;
    }
    
  
    @Override
    public void run() 
    {
        String received;
        
        while (true) 
        {
            try {
  received= clientInput.readLine();
  
                Action action = gson.fromJson(received, new TypeToken<Action>(){}.getType());
                ActionType actionType = action.getActionType();
                switch (actionType) {
                    case Login:
                        RespondToLogin(action);
                        break;
                    default:
                        throw new AssertionError();
                }
             
                
            } catch (IOException e) {
            }
        }
          
    
       }

    private void RespondToLogin(Action action) {
        LoginActionResponse actionResponse = new LoginActionResponse();
        User user =gson.fromJson(action.getActionParamsJSON(), User.class);
     User foundUser =  server.users.stream().filter(u->u.getUserName().equals(user.getUserName())).findFirst().orElse(null);
     
     if(foundUser == null){
            actionResponse.setIsSuccess(false);
         sendToClient(actionResponse);  
        }
      if(user.getPasswordHash().equals(foundUser.getPasswordHash()))
      {
          actionResponse.setIsSuccess(true);
          actionResponse.setUserId(foundUser.getUserId());
          actionResponse.setRank(foundUser.getRank());
          sendToClient(actionResponse);
         
          return;
      }
      actionResponse.setIsSuccess(false);
          sendToClient(actionResponse);
       
    }

    private void sendToClient(LoginActionResponse actionResponse) {
        String data = gson.toJson(actionResponse);
        try {
            serverOutput.write(data+"\r\n");
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}