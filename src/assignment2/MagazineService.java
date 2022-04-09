/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author 33961412
 */
public class MagazineService implements Serializable {
    
    private Magazine magazine = new Magazine();
    private Subscription subscription = new Subscription();

    public MagazineService(Magazine magazine, Subscription subscription) {
        this.magazine = magazine;
        this.subscription = subscription;
    }

    public MagazineService() {
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    //get all supplements for a customer

    public String getSupplements(int customerId) {
        String supplements = "";
        ArrayList<Supplement> customerSupplements = subscription.getSupplements(customerId);

        for(Supplement supplement : customerSupplements) {
            supplements += supplement.getName() + "\n";
        }

        return supplements;
    }

    public String getPayingCustomerString(int customerId) {
        String payingCustomer = "";

        payingCustomer = magazine.getPayingCustomerOfAssociate(customerId).getName();

        return payingCustomer;
    }
    
    public ArrayList<Supplement> getSups() {
        return magazine.getSupplements();
    }

    public String[] getCustomersNames() {
        String[] customerNames = null;
        
        try{
            customerNames = magazine.getCustomerNames();

        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }

        return customerNames;
        
    }

    public String[] getSupplementsName() {
        String[] supplements = null;

        try{
            supplements = magazine.getSupplementNames();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return supplements;
    }

    public String[] getPayingCusName() {
        String[] payingCustomer = null;

        try{
            payingCustomer = magazine.getPayingCustomerStrings();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return payingCustomer;
    }

    public Supplement findSupplement(String supplementName) {
        Supplement supplement = null;
        try{
            supplement = magazine.getSupplementByName(supplementName);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return supplement;
    }

    public void writeMagazineService(File saveFile) {
        try {
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("Writing to file...");
            out.writeObject(magazine);
            out.writeObject(subscription);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void readMagazineService(File openFile) {

        try {
            FileInputStream fileIn = new FileInputStream(openFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            System.out.println("Reading from file...");
            this.magazine = (Magazine) in.readObject();
            this.subscription = (Subscription) in.readObject();
            in.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        

    }

    public Supplement getSupplement(String supplementName) {
        return magazine.getSupplementByName(supplementName);
    }

    public Customer getCustomer(String customerName) {
        return magazine.getCustomerByName(customerName);
    }

    public String getAssociateCustomerByPayingCustomer(int customerId) {
        //returns an array of associate customer names for a specific paying customer
        //return magazine.getAssociateCustomerByPayingCustomer(customerId);
        String associateCustomer = "";
        String[] list = magazine.getAssociateCustomerByPayingCustomer(customerId);

        for(String s : list) {
            associateCustomer += s + "\n";
        }

        return associateCustomer;
    }

    public String getBillStrings(int customerId) {

        Customer customer = magazine.getCustomerById(customerId);
        String bill = "";
        String[] billStrings = Calculation.getWeeklySuppList(customer, subscription, magazine);

        for (String s : billStrings) {
            if (s != null) {
                bill += s + "\n";
            }
        }

        return bill;
    }

    public void deleteSupplement(String s){
        magazine.deleteSupplement(s);
    }

    public void deleteCustomer(String s){
        magazine.deleteCustomer(s);
    }
}
