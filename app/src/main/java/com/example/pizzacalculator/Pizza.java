package com.example.pizzacalculator;

import java.io.Serializable;

public class Pizza implements Serializable {


    boolean isDelivery;
    StringBuilder toppings;
    int topping_num;

    public boolean isDelivery(){
        return isDelivery;
    }
    public StringBuilder getToppings(){
        return toppings;
    }
    public int getTopping_num(){
        return topping_num;
    }
    public Pizza(boolean isDelivery, int topping_num, StringBuilder toppings){
        this.isDelivery = isDelivery;
        this.topping_num = topping_num;
        this.toppings = toppings;
    }

}
