/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 * @title PayingCustomer
 * @description This class is used to create a paying customer
 * @filename PayingCustomer.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class PayingCustomer extends Customer {

    private String paymentMethod;
    private ArrayList<AssociateCustomer> associateList = new ArrayList<AssociateCustomer>();

    /**
     * Default constructor for PayingCustomer
     */
    public PayingCustomer() {
        super();
        this.paymentMethod = "";
    }

    /**
     * Non-Default constructor for PayingCustomer
     * 
     * @param name
     * @param email
     * @param paymentMethod
     */
    public PayingCustomer(String name, String email, String paymentMethod) {
        super(name, email);
        this.paymentMethod = paymentMethod;
    }

    /**
     * Getter for payment method
     * 
     * @return paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Checks if the payment method is valid c = credit, d = debit

    /**
     * Setter for payment method
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(String paymentMethod) {

        // check if payment method is credit or debit
        if (paymentMethod.equals("c".toLowerCase().trim()) || paymentMethod.equals("d".toLowerCase().trim())) {
            this.paymentMethod = paymentMethod;
        } else {
            System.out.println("Invalid payment method");
        }
    }

    // retrns list of associate customers for paying customer

    /**
     * Getter for associate list
     * 
     * @return associateList
     */
    public ArrayList<AssociateCustomer> getAssociateList() {
        return associateList;
    }

    // adds associate customer to associate list

    /**
     * Adds associate customer to associate list
     * 
     * @param associate
     * @return true if associate is valid
     */
    public boolean addAssociate(AssociateCustomer associate) {

        // check if associate customer is already in list
        if (!associateList.contains(associate)) {
            associateList.add(associate);
            return true;
        } else {
            return false;
        }
    }
}
