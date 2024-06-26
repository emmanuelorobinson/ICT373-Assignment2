/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @title Customer
 * @description This is the super class which sets up the base for both paying
 *              and associate customer.
 * @filename Customer.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class Customer implements Serializable {

    private static AtomicInteger id = new AtomicInteger(0);
    private int customerId;
    private String name;
    private String email;
    private Address address;

    /**
     * Default constructor for Customer
     */
    public Customer() {
        this.customerId = id.incrementAndGet();
        this.name = "invalid name";
        this.email = "invalidEmail@exmple.com";
        this.address = new Address();
    }

    /**
     * Non-default constructor for Customer
     * 
     * @param name
     * @param email
     */
    public Customer(String name, String email) {
        this.customerId = id.incrementAndGet();
        this.name = name.toLowerCase();
        this.email = email.toLowerCase();
        this.address = new Address();
    }

    /**
     * Getter for customer id
     * 
     * @return returns customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    // return customer by id

    /**
     * Getter for customer name
     * 
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Setter for customer name
     * 
     * @param name
     * @return
     */
    public boolean setName(String name) {

        if (name.length() > 0) {
            this.name = name.toLowerCase();
            return true;
        }
        return false;
    }

    /**
     * Getter for customer email
     * 
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for customer email
     * 
     * @param email
     * @return
     */
    public boolean setEmail(String email) {

        if (email.length() > 0) {
            this.email = email.toLowerCase();
            return true;
        }
        return false;
    }

    /**
     * equals method for customer
     * 
     * @param customer
     * @return
     */
    public boolean equals(Customer customer) {
        if (this.name == customer.getName() && this.email == customer.getEmail()) {
            return true;
        }
        return false;
    }

    //gettes and setters for address
    /**
     * Getter for customer address
     * 
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for customer address
     * 
     * @param address
     * @return
     */

    public boolean setAddress(Address address) {
        if (address != null) {
            this.address = address;
            return true;
        }
        return false;
    }

    public String printCustomer() {
        return "Customer ID: " + customerId + "\n" + "Name: " + name + "\n" + "Email: " + email;
    }

    public class Address implements Serializable {

        int streetNo = 0;
        String streetName = "Invalid Street Name";
        String suburb = "Invalid Suburb";
        int postcode = 0;


        /**
         * Default constructor for Address
         */
        public Address() {
            this.streetNo = 0;
            this.streetName = "Invalid Street Name";
            this.suburb = "Invalid Suburb";
            this.postcode = 0;
        }

        /**
         * Non-Default constructor for Address
         */

        public Address(int streetNo, String streetName, String suburb, int postcode) {
            this.streetNo = streetNo;
            this.streetName = streetName.toLowerCase();
            this.suburb = suburb.toLowerCase();
            this.postcode = postcode;
        }

        //getters and setters
        public int getStreetNo() {
            return streetNo;
        }

        public void setStreetNo(int streetNo) {
            this.streetNo = streetNo;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName.toLowerCase();
        }

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb.toLowerCase();
        }

        public int getPostcode() {
            return postcode;
        }

        public void setPostcode(int postcode) {
            this.postcode = postcode;
        }

        public String printAddress() {
            return "Street Number: " + streetNo + "\n" + "Street Name: " + streetName + "\n" + "Suburb: " + suburb + "\n"
                    + "Postcode: " + postcode;
        }


    }
}
