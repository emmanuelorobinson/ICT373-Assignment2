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
public class MangazineService implements Serializable {
    
    private Magazine magazine = new Magazine();
    private Subscription subscription = new Subscription();

    public MangazineService(Magazine magazine, Subscription subscription) {
        this.magazine = magazine;
        this.subscription = subscription;
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


}
