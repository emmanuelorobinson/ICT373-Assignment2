/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 * @title Calulation
 * @description This perform calculations need to get weekly and monthly emails
 *              for the customers.
 * @filename Calulation.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */

public class Calculation {

    /**
     * Generates monthly email for all paying customers
     * 
     * @param mag
     * @param sub
     */
    public static void getMonthlyCostEmail(Magazine mag, Subscription sub) {
        // for each customer in magazine call getMonthlyCost
        ArrayList<Customer> customers = mag.getCustomerList();

        for (Customer customer : customers) {

            // loop through for paying customer only
            if (customer instanceof PayingCustomer) {
                // call method for calculation on paying customer
                getMonthlyCost((PayingCustomer) customer, sub, mag);
            }

        }
    }

    /**
     * Generates weekly email for all customers
     * 
     * @param mag
     * @param sub
     */
    public static void getWeeklyEmail(Magazine mag, Subscription sub) {
        // for each customer in magazine call getMonthlyCost
        ArrayList<Customer> customers = mag.getCustomerList();
        int week = 1;

        while (week <= 4) {

            System.out.println("WEEK " + week);

            for (Customer customer : customers) {
                getWeeklySuppList(customer, sub, mag);
            }
            week++;

        }
    }

    /**
     * Perfoms the calculation needed for the end of the month email.
     * 
     * @param pCustomer
     * @param sub
     * @param mag
     */
    public static void getMonthlyCost(PayingCustomer pCustomer, Subscription sub, Magazine mag) {
        int numOfWeek = 4;
        float total = 0;

        // get supplements for customer
        ArrayList<Supplement> supplements;

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("END OF THE MONTH ACCOUNT STATEMENT");

        // print out supplements details
        System.out.println("\nPAYING CUSTOMER: " + pCustomer.getName());
        System.out.println("EMAIL: " + pCustomer.getEmail());
        System.out
                .println("Subscibed to: " + mag.getTitle() + " :: Monthly Cost: $" + (mag.getWeeklyCost() * numOfWeek));
        total = mag.getWeeklyCost() * numOfWeek;
        System.out.println(
                "---------------------------------------------------------------------");

        // get supplements for customer
        if (sub.getSupplements(pCustomer.getCustomerId()) != null) {
            supplements = sub.getSupplements(pCustomer.getCustomerId());

            // retreives supplement for paying customer
            for (Supplement supplement : supplements) {
                System.out.println(supplement.getCostDetails(numOfWeek));
                total += supplement.getCost() * numOfWeek;
            }

        } else {
            // returns string if no supplement for paying customer is found
            System.out.println("No supplements for this customer");
        }

        System.out.println("");

        // for each associated customer get supplements
        System.out.println("PAYING FOR: ");
        System.out.println(
                "#####################################################################");

        // gets all associated customers from paying customer object
        for (AssociateCustomer associatedCustomer : pCustomer.getAssociateList()) {

            // check if associate customer is part of the magazine
            if (!mag.getCustomerList().contains(associatedCustomer)) {
                return;
            }

            ArrayList<Supplement> associatedSupplements;

            // check if sub.getSupplements is null
            System.out.println("ASSOCIATE CUSTOMER: " + associatedCustomer.getName());

            /// checks the subscription object if custome has supplements. if not, it
            /// returns null
            if (sub.getSupplements(associatedCustomer.getCustomerId()) != null) {
                associatedSupplements = sub.getSupplements(associatedCustomer.getCustomerId());

                // retreives supplement for associate customer
                for (Supplement supplement : associatedSupplements) {
                    System.out.println(supplement.getCostDetails(numOfWeek));
                    total += supplement.getCost() * numOfWeek;
                }

                System.out.println("");

            } else {
                System.out.println("No supplements for this customer");
                System.out.println("");
            }

        }

        System.out.println("Month Total: $" + total);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");

    }

    /**
     *
     * Performs the calculation needed for the weekly email.
     * 
     * @param customer
     * @param sub
     * @param mag
     */
    public static String[] getWeeklySuppList(Customer customer, Subscription sub, Magazine mag) {

        // get supplements for customer
        String[] billings = new String[20];
        ArrayList<Supplement> supplements;
        int numOfWeek = 1;
        int count = 0;

        //System.out.println("WEEKLY MAGAZINE FOR " + mag.getTitle().toUpperCase() + " OUT NOW!");
        billings[count] = "WEEKLY MAGAZINE FOR " + mag.getTitle().toUpperCase() + " OUT NOW!";
        count++;
        // print out supplements details
        //System.out.println("\nNAME: " + customer.getName());
        billings[count] = "NAME: " + customer.getName();
        count++;

        //System.out.println("EMAIL: " + customer.getEmail());
        billings[count] = "EMAIL: " + customer.getEmail();
        count++;
        //System.out.println(
        //        "---------------------------------------------------------------------");
        
        billings[count] = "---------------------------------------------------------------------";
        // checks subscription object if customer has supplements
        if (sub.getSupplements(customer.getCustomerId()) != null) {
            supplements = sub.getSupplements(customer.getCustomerId());

            // retreives supplement for all customer
            for (Supplement supplement : supplements) {
                //System.out.println(supplement.getCostDetails(numOfWeek));
                if(supplement.getCostDetails(numOfWeek) != null) {
                    billings[count] = supplement.getCostDetails(numOfWeek);
                    count++;
                }
            }
        } else {
            //System.out.println("No supplements for this customer");
            billings[count] = "No supplements for this customer";
            count++;
        }

        return billings;

    }
}
