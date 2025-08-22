package com.example.demo.pack2;


public class User {

    private String name;
    private String s = "Sahan";
    private String update;


    public String getName() {
        return name.isEmpty() ? "Name Is Empty": name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateName(String update) {
        this.update = update;
        this.s = s.concat(update);
        System.out.println(s);
    }

    public void removeName(String update) {
        this.s = s.replace(update, "");
        this.update = update;
        System.out.println(s);
    }
}
