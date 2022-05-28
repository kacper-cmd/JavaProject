/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commonClasses;

import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class Order implements Comparable<Order>{
  int orderId;
    int userId;
    ArrayList<MenuItem> ordeitems;
  

    @Override
    public String toString() {
        return "Order{" + " userId=" + userId + ", ordeitems=" + ordeitems  + '}';
    }

   
    public Order(int orderId, int userId, ArrayList<MenuItem> ordeitems) {
        this.orderId = orderId;
        this.userId = userId;
        this.ordeitems = ordeitems;
           
    }
  public Order(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
        ordeitems = new ArrayList<>();
       
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<MenuItem> getOrdeitems() {
        return ordeitems;
    }
    public void addToOrder(MenuItem me){
        ordeitems.add(me);
    }
          void removeFromOrder( int index) throws Exception{
          if(ordeitems.isEmpty() || index >= ordeitems.size()) throw new Exception("index out of bounds or empty list");
        ordeitems.remove(index);
    }

    public void setOrdeitems(ArrayList<MenuItem> ordeitems) {
        this.ordeitems = ordeitems;
    }

    @Override
    public int compareTo(Order o) {
       return Integer.compare(orderId, o.orderId);
    }
    
}
