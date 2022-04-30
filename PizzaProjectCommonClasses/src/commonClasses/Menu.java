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
public class Menu {
    ArrayList<MenuItem> menuItems;
   public void addMenuItem(MenuItem me){
        menuItems.add(me);
    }
    public void removeMenuItem( int index) throws Exception{
          if(menuItems.isEmpty() || index >= menuItems.size()) throw new Exception("index out of bounds or empty list");
        menuItems.remove(index);
    }
public Menu(){
    menuItems = new ArrayList<>();
    
}
    public Menu(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    
      public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    
    public void editMenuItem(int index, MenuItem menuItem) {
       if (index >= menuItems.size()) return;
       menuItems.set(index, menuItem);
    }
    
}
