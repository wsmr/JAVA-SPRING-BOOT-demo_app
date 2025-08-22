package com.example.demo.pack1;




public class Bank {

    protected double amount = 0;


    protected void withdraw(int val) {
        amount =- val;
        System.out.println(amount);
    }

    protected void saving(double val) {
        amount =+ val;
        System.out.println(amount);
    }
}
