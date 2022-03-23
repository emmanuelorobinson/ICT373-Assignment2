/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;

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

    public String getAllSupplements(int customerId) {
        String supplements = "";

        if (subscription.getSupplements(customerId).size() > 0) {

            for (Supplement supplement : subscription.getSupplements(customerId)) {
                supplements += supplement.getName() + "\n";
            }
        }
        return supplements;
    }

    public String getAllCustomersNames() {
        String customers = "";

        for (Customer customer : magazine.getCustomerList()) {
            customers += customer.getName() + "\n";
        }
        return customers;
    }

    public String getSupplementsName() {
        String supplements = "";

        for (Supplement supplement : magazine.getSupplements()) {
            supplements += supplement.getName() + "\n";
        }
        return supplements;
    }

}
