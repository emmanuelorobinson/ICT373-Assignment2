/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @title Magazine
 * @description This class is used to create a magazine object.
 * @filename Magazine.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class Magazine implements Serializable {
    private String title;
    private float weeklyCost;
    private ArrayList<Supplement> supplementList = new ArrayList<Supplement>();
    private ArrayList<Customer> customerList = new ArrayList<Customer>();

    /**
     * Default constructor for Magazine
     */
    public Magazine() {
        this.title = "";
        this.weeklyCost = 0;
    }

    /**
     * Non-default constructor for Magazine
     * 
     * @param title
     * @param weeklyCost
     */
    public Magazine(String title, float weeklyCost) {
        this.title = title;
        this.weeklyCost = weeklyCost;
    }

    /**
     * Getter for magazine title
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for magazine title
     * 
     * @param title
     * @return true if title is valid
     */
    public boolean setTitle(String title) {

        if (title.length() > 0) {
            this.title = title.toLowerCase();
            return true;
        }
        return false;
    }

    /**
     * Getter for magazine weekly cost
     * 
     * @return weeklyCost
     */
    public float getWeeklyCost() {
        return weeklyCost;
    }

    /**
     * Setter for magazine weekly cost
     * 
     * @param weeklyCost
     * @return true if weeklyCost is valid
     */
    public boolean setWeeklyCost(float weeklyCost) {

        if (weeklyCost >= 0) {
            this.weeklyCost = weeklyCost;
            return true;
        }
        return false;

    }

    /**
     * Getter for supplement list
     * 
     * @return supplementList
     */
    public ArrayList<Supplement> getSupplements() {
        return supplementList;
    }

    /**
     * adds suuplement to supplement list
     * 
     * @param supplement
     * @return true if supplement is valid
     */
    public boolean addSupplement(Supplement supplement) {

        if (supplement != null) {
            if (!supplementList.contains(supplement)) {
                supplementList.add(supplement);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Gets supplement list from magazine
     * 
     * @return supplementList
     */
    public ArrayList<Supplement> getSuppList() {
        return supplementList;
    }

    // method allows the retrieval of a supplement by its name

    /**
     * Gets supplement by name
     * 
     * @param name
     * @return supplement
     */
    public Supplement getSupplementByName(String name) {
        for (Supplement s : supplementList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Getter for customer list
     * 
     * @return customerList
     */
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * adds customer to the magazine customer list
     * 
     * @param customer
     * @return true if customer is valid
     */
    public boolean addCustomer(Customer customer) {

        if (customerList.contains(customer)) {
            return false;
        } else {
            customerList.add(customer);
            return true;
        }
    }

    /**
     * Removes a customer from the magazine customer list
     * 
     * @param customer
     */
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }

    // method allows the retrieval of a customer by its ID

    /**
     * Gets customer by id
     * 
     * @param id
     * @return customer
     */
    public Customer getCustomerById(int id) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == id) {
                return (Customer) customer;
            }
        }
        return null;
    }

    /**
     * Gets customer by name
     * 
     * @param name
     * @return customer
     */

    public Customer getCustomerByName(String name) {
        for (Customer customer : customerList) {
            if (customer.getName().equals(name)) {
                return (Customer) customer;
            }
        }
        return null;
    }

    public String[] getCustomerNames() {
        String[] studentNames = new String[customerList.size()];
        int i = 0;
        for (Customer customer : customerList) {
            if (customer instanceof Customer) {
                studentNames[i] = customer.getName();
                i++;
            }
        }
        return studentNames;
    }

    public String[] getSupplementNames() {
        String[] supplementNames = new String[supplementList.size()];
        int i = 0;
        for (Supplement supplement : supplementList) {
            if (supplement instanceof Supplement) {
                supplementNames[i] = supplement.getName();
                i++;
            }
        }
        return supplementNames;
    }
}
