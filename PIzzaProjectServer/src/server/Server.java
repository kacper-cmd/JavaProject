/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import commonClasses.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class Server implements Runnable {
    Thread t;
    ArrayList<Order> orders;
    int lastOrderId ;
    int lastUserId;
    Menu menu;
    ArrayList<User> users;
    public Server() {
        
       
        t = new Thread(this);
        System.out.println("Starting Server: " + t);
        exit = false;
       
    }
    public void addUser(User user){
        lastUserId++;
        user.setUserId(lastUserId);
users.add(user);
    }
     public void addMenuItem(MenuItem menuItem){
        menu.addMenuItem(menuItem);
    }
      public void editMenuItem(int indexmenuItem, MenuItem menuItem){
        menu.editMenuItem(indexmenuItem, menuItem);
    }
       public void addOrder(Order order, User user){
               lastOrderId++;
               order.setUserId(user.getUserId());
        order.setUserId(lastOrderId);
        orders.add(order);
    }
        public void editOrder(Order order, int index){
        if (index >= orders.size()) return;
        
    }
    public  void start(){
        Gson gson = new Gson();
        Path path = Paths.get("users.json");
          String fc; 
        if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
          
            try {
                 fc = Files.readString(path);
            } catch (IOException ex) {
                fc = "";
            }
            if(!fc.equalsIgnoreCase("")){
                users = gson.fromJson(fc, new TypeToken<ArrayList<User>>(){}.getType());
            }
            else
            {
                users = new ArrayList<>();
            }
          
             path = Paths.get("menu.json");
        }
        if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
   
            try {
                 fc = Files.readString(path);
            } catch (IOException ex) {
                fc = "";
            }
            if(!fc.equalsIgnoreCase("")){
                menu = gson.fromJson(fc, new TypeToken<Menu>(){}.getType());
            }
            else
            {
                menu = new Menu();
            }
        }
         path = Paths.get("orders.json");
        if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
   
            try {
                 fc = Files.readString(path);
            } catch (IOException ex) {
                fc = "";
            }
            if(!fc.equalsIgnoreCase("")){
                orders = gson.fromJson(fc, new TypeToken<ArrayList<Order>>(){}.getType());
            }
            else
            {
                orders = new ArrayList<>();
            }
        }
        if(users.isEmpty()){
            lastUserId =0;
        }else{
            Collections.sort(users);
            lastUserId = users.get(users.size()-1).getUserId();
        }
       
         if(orders.isEmpty()){
            lastOrderId =0;
        }else{
            Collections.sort(orders);
            lastOrderId = orders.get(orders.size()-1).getUserId();
        }
        t.start();
    }
    
    
    private boolean exit;
      public void stop()
    {
        exit = true;
    }
   @Override
    public void run () 
    {
       try {
           startServer();
       } catch (IOException ex) {
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       }
          
    }
        private void startServer() throws IOException {
// server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
          
        // running infinite loop for getting
        // client request
        while (!exit) 
        {
            Socket s = null;
              ss.setSoTimeout(1000);
            try 
            {
                
                // socket object to receive incoming client requests
                s = ss.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                  
                System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos,this);
  
                // Invoking the start() method
                t.start();
                  
            }
            catch(SocketTimeoutException e)
            {
            }
            catch (Exception e){
                if(s != null)
                s.close();
                e.printStackTrace();
            }
            
}
       Gson gson = new Gson();
       var userJSON = gson.toJson(users);
       Files.writeString(Paths.get("users.json"), userJSON, StandardOpenOption.TRUNCATE_EXISTING);
  var menuJSON = gson.toJson(menu);
       Files.writeString(Paths.get("menu.json"), menuJSON, StandardOpenOption.TRUNCATE_EXISTING);
         var ordersJSON = gson.toJson(orders);
       Files.writeString(Paths.get("orders.json"), ordersJSON, StandardOpenOption.TRUNCATE_EXISTING);
    
}
}