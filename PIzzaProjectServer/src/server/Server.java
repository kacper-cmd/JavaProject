/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import commonClasses.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 20
 */
public class Server implements Runnable {
    Thread t;
     ArrayList<Order> orders;
    private int lastOrderId ;
    private int lastUserId;
    public Menu menu;
    ArrayList<User> users;
    public Server() {
        
       
        t = new Thread(this);
        System.out.println("Starting Server: " + t);
        exit = false;
       
    }
    public boolean addUser(User user){
        for (int i = 0; i < users.size(); i++) {
           if( users.get(i).getEmail().equals(user.getEmail())){
               return false;
           }
         
        }
        lastUserId++;
        user.setUserId(lastUserId);
        user.setRank(Rank.StandardUser);
users.add(user);
return true;
    }
     public void addMenuItem(MenuItem menuItem){
        menu.addMenuItem(menuItem);
    }
        public void setMenu(Menu menu){
        this.menu =menu;
    }
//      public void editMenuItem(int indexmenuItem, MenuItem menuItem){
//        menu.editMenuItem(indexmenuItem, menuItem);
//    }
       public void addOrder(Order order, User user){
               lastOrderId++;
               order.setUserId(user.getUserId());
        order.setOrderId(lastOrderId);
        orders.add(order);
    }
        public boolean editOrder(Order ord){
                 
                  int orderIndex= -1;

            for (int i = 0; i < orders.size(); i++) {
                var loopObject = orders.get(i);
                if(loopObject.getOrderId() == ord.getOrderId()){
                    orderIndex = i;
                }
            }
            
        if( orderIndex== -1){
            return false;
        }
         orders.set(orderIndex, ord);
         return true;
        
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
          
            
        }
        else{
            users = new ArrayList<>();
        }
         path = Paths.get("menu.json");
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
         else{
            menu = new Menu();
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
         else{
            orders = new ArrayList<>();
        }
        if(users.isEmpty()){
            lastUserId =0;
        }else{
            Collections.sort(users);
            lastUserId = users.get(users.size()-1).getUserId();
        }
       
//         if(orders.isEmpty()){
//            lastOrderId =0;
//        }else{
//            Collections.sort(orders);
//            lastOrderId = orders.get(orders.size()-1).getUserId();
//        }
         
        
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
       FileWriter usersWriter = new FileWriter("users.json",false);
      usersWriter.write(userJSON);
      usersWriter.close();
  var menuJSON = gson.toJson(menu);
    FileWriter menuWriter = new FileWriter("menu.json",false);
      menuWriter.write(menuJSON);
      menuWriter.close();

         var ordersJSON = gson.toJson(orders);
           FileWriter orderWriter = new FileWriter("orders.json",false);
      orderWriter.write(ordersJSON);
      orderWriter.close();

    
}
}