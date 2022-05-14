/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commonClasses;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author PC
 */
public class MenuItem {

  String name;
String ingredients;
  byte [] image;
  double price;
    public MenuItem(String name, String ingredients, byte[] image, double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

   
}
