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
public class GetOrdersActionResponse {
     boolean success;
    ArrayList<Order> orders;
    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
