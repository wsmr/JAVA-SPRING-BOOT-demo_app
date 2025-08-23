package com.example.demo.pack1;


import static com.example.demo.pack1.ActivityTypes.*;

public class Branch extends Bank {

    private String name;
    private String manager;

//    @Override
//    public void withdraw() {
////        super.withdraw();
//        System.out.println(amount);
//    }

    public String getAmount() {
        return ("Bank Amount is " + amount);
    }

    public void activity(String type, final double val) {

        switch (type) {
            case WITHDRAWAL:
                System.out.println("Withdrew");

                if (amount >= val) withdraw((int) val);
                break;

            case SAVING:
                System.out.println("Saving");
                saving(val);
                break;

            case FIXED:
                System.out.println("fix");
                fix();
                break;

            case LOAN:
                System.out.println("loan");
                loan();
                break;

            default:
                System.out.println("default");
        }
    }


    public void fix() {
        System.out.println("Set Fixed Deposit" + amount);
    }


    public void loan() {
        System.out.println("Get Loan" + amount);
    }


}
