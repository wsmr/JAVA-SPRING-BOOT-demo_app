package com.example.demo.pack3;

import com.example.demo.pack1.Bank;

public class Staff extends Bank {

    Bank bank = new Bank();

    private String name;

    // Default constructor
    public Staff() {
        super();
    }

    // Constructor with name
    public Staff(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Staff{name='" + name + "'}";
    }
}
