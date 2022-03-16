/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @title Subscription
 * @description This class allows for the connection of a customer and list of
 *              supplements
 * @filename Subscription.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class Subscription implements Serializable {
    // links a customer to its supplements of choice
    private HashMap<Integer, ArrayList<Supplement>> subscription = new HashMap<Integer, ArrayList<Supplement>>();

    /**
     * Default constructor for Subscription
     */
    public Subscription() {
        this.subscription = new HashMap<Integer, ArrayList<Supplement>>();
    }

    /**
     * Adds supplement to hashmap
     * 
     * @param customerId
     * @param supplement
     * @param magazine
     * @return true if customer is valid
     */
    public boolean addSupplement(int customerId, Supplement supplement, Magazine magazine) {
        // checks if magazine contains supplement trying to be added
        if (magazine.getSupplements().contains(supplement)) {

            if (subscription.containsKey(customerId)) {

                // check if customer already has this supplement
                if (!subscription.get(customerId).contains(supplement)) {
                    subscription.get(customerId).add(supplement);
                    return true;
                } else {
                    System.out.println("Customer already has this supplement");
                    return false;
                }

            } else {
                ArrayList<Supplement> supplements = new ArrayList<Supplement>();

                // check if customer already has this supplement
                supplements.add(supplement);
                subscription.put(customerId, supplements);
                return true;

            }
        } else {
            System.out.println("Magazine does not contain this supplement");
            return false;
        }
    }

    /**
     * Gets supplements for customer by ID
     * 
     * @param customerId
     * @return supplements
     */
    public ArrayList<Supplement> getSupplements(int customerId) {
        return subscription.get(customerId);
    }
}
