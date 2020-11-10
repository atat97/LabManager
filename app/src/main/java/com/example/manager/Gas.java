package com.example.manager;

public class Gas {
    String name;
    String amount;

    public Gas(){}

    public Gas(String name, String amount){
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }
}
