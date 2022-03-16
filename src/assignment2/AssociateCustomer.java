/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 * @title AssociateCustomer
 * @description This class is used to create an associate customer
 * @filename AssociateCustomer.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */

public class AssociateCustomer extends Customer {

    private PayingCustomer payingCustomer;

    /**
     * Defualt constructor for AssociateCustomer
     */
    public AssociateCustomer() {
        super();
    }

    /**
     * Non-default constructor for AssociateCustomer
     * 
     * @param name
     * @param email
     */
    public AssociateCustomer(String name, String email) {
        super(name, email);
    }

    /**
     * Setter for associate customer. Allows the paying customer to be set for an
     * associate customer
     * 
     * @param payingCustomer
     */
    public void setPayingCustomer(PayingCustomer payingCustomer) {
        this.payingCustomer = payingCustomer;

        // adds associate customer to associate list of paying customer
        payingCustomer.addAssociate(this);
    }

    /**
     *
     * Gets the associate customer's paying customer
     * 
     * @return the paying customer
     */
    public PayingCustomer getPayingCustomer() {
        return payingCustomer;
    }
}
