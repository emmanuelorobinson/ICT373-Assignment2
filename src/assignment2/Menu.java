/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @title Menu
 * @description This class is used to display the menu and call the appropriate
 * @filename Menu.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class Menu {

    /**
     *
     */
    public static void menu() {
        Scanner sc = new Scanner(System.in);

        Magazine mag = new Magazine();
        Subscription sub = new Subscription();
        int option = 0;

        while (option != 9) {

            System.out.println("1. Create/Change Magazine");
            System.out.println("2. Add Supplement to Magazine");
            System.out.println("3. Create and Add Paying Customers to Magazine");
            System.out.println("4. Create and Add Associate Customer to Magazine");
            System.out.println("5. Select Supplement for Customer by ID");
            System.out.println("6. Remove Customer from Magazine");
            System.out.println("7. Get 4 Weeks of Magazine Email for each Customer");
            System.out.println("8. Generate End of Month Cost Report for Paying Customers");
            System.out.println("9. Exit");

            System.out.println("\nPlease go through 1-6 in order to add customers and supplements");
            System.out.println("Last choosen option: " + option);

            System.out.println("Enter your option: ");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    addMagazine(mag);
                    break;

                case 2:
                    addSupplement(mag);
                    break;

                case 3:
                    addPayingCustomer(mag);
                    break;

                case 4:
                    addAssociateCustomer(mag);
                    break;

                case 5:
                    addCustomerSupplement(mag, sub);
                    break;

                case 6:
                    removeCustomer(mag);
                    break;

                case 7:
                    Calculation.getWeeklyEmail(mag, sub);
                    System.out.println("Press any key to continue");
                    sc.next();
                    break;

                case 8:
                    Calculation.getMonthlyCostEmail(mag, sub);
                    System.out.println("Press any key to continue");
                    sc.next();

                    break;
                default:
                    break;
            }
        }

    }

    public static void writeSerializedObject(ObjectOutputStream oos, Magazine mag, Subscription sub) {
        try {
            oos.writeObject(mag);
            oos.writeObject(sub);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void readSerializedObject(ObjectInputStream ois, Magazine mag, Subscription sub) {
        try {
            mag = (Magazine) ois.readObject();
            sub = (Subscription) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Creates a magazine
     * 
     * @param mag
     */
    public static void addMagazine(Magazine mag) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Magazine Name: ");
        mag.setTitle(sc.next());

        System.out.println("Enter Magazine Cost: ");
        mag.setWeeklyCost(sc.nextFloat());

    }

    /**
     * Creates a supplement and adds it to the magazine
     * 
     * @param mag
     */
    public static void addSupplement(Magazine mag) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            Supplement supplement = new Supplement();

            System.out.println("Enter Supplement Name: ");
            supplement.setName(sc.next());

            System.out.println("Enter Supplement Cost: ");
            supplement.setCost(sc.nextFloat());

            mag.addSupplement(supplement);

            System.out.println("Do you want to add another supplement? (y/n)");
            if (sc.next().equals("n")) {
                break;
            }
        }

        //
    }

    /**
     * Creates a paying customer and adds it to the magazine
     * 
     * @param mag
     */
    public static void addPayingCustomer(Magazine mag) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            PayingCustomer customer = new PayingCustomer();

            System.out.println("Enter Paying Customer Name: ");
            customer.setName(sc.next());

            System.out.println("Enter Paying Customer Email: ");
            customer.setEmail(sc.next());

            System.out.println("Enter Customer Payment Method (c/d): ");
            customer.setPaymentMethod(sc.next());

            // adding customer to magazine service
            mag.addCustomer(customer);

            System.out.println("Do you want to add another customer? (y/n)");
            if (sc.next().toLowerCase().equals("n")) {
                break;
            }
        }

    }

    /**
     * Creates an associate customer and adds it to the magazine
     * 
     * @param mag
     */
    public static void addAssociateCustomer(Magazine mag) {
        Scanner sc = new Scanner(System.in);

        // prinout all paying customer in magazine
        System.out.println("Paying Customers in magazine: ");

        // printing all the paying customer in magazine
        mag.getCustomerList().forEach((customer) -> {
            if (customer instanceof PayingCustomer) {
                System.out.println("ID: " + customer.getCustomerId() + " Name: " + customer.getName());
            }
        });

        while (true) {
            AssociateCustomer customer = new AssociateCustomer();

            System.out.println("Enter Associate Customer Name: ");
            customer.setName(sc.next());

            System.out.println("Enter Associate Customer Email: ");
            customer.setEmail(sc.next());

            System.out.println("Please type in the ID of the paying customer: ");
            PayingCustomer payingCustomer = (PayingCustomer) mag.getCustomerById(sc.nextInt());
            customer.setPayingCustomer(payingCustomer);

            // adding customer to magazine service
            mag.addCustomer(customer);

            System.out.println("Do you want to add another customer? (y/n)");
            if (sc.next().equals("n")) {
                break;
            }
        }

    }

    /**
     * Adds a supplement to a customer
     * 
     * @param mag
     * @param sub
     */
    public static void addCustomerSupplement(Magazine mag, Subscription sub) {
        Scanner sc = new Scanner(System.in);

        System.out.println("List of customers and ID: ");
        mag.getCustomerList().forEach((customer) -> {
            System.out.println("ID: " + customer.getCustomerId() + " Name: " + customer.getName());
        });

        // prinout all supplement in magazine
        System.out.println("\nList of Supplements");
        mag.getSuppList().forEach((supplement) -> {
            System.out.println(supplement.getName().toLowerCase());
        });

        while (true) {

            System.out.println("Please type in the ID of the customer to add supplement for: ");
            Customer customer = mag.getCustomerById(sc.nextInt());

            System.out.println("Please type in the name of the supplement to add: ");
            Supplement supplement = mag.getSupplementByName(sc.next());

            sub.addSupplement(customer.getCustomerId(), supplement, mag);

            System.out.println("Do you want to add another supplement? (y/n)");
            if (sc.next().equals("n")) {
                break;
            }
        }

    }

    /**
     * Removes a customer from the magazine
     * 
     * @param mag
     */
    public static void removeCustomer(Magazine mag) {
        Scanner sc = new Scanner(System.in);

        // prinout all supplement in magazine
        mag.getCustomerList().forEach((customer) -> {
            System.out.println("ID: " + customer.getCustomerId() + " Name: " + customer.getName());
        });

        System.out.println("Please type in the ID of the customer to remove: ");
        mag.removeCustomer(mag.getCustomerById(sc.nextInt()));

    }
}
