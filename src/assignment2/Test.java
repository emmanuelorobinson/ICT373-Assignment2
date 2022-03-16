/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author DELL
 */
public class Test {

    /**
     * @param args the command line arguments
     */

    public static void test() {
        Magazine mag = new Magazine("The Daily Express", 10.0f);
        Subscription sub = new Subscription();

        Supplement sup1 = new Supplement("Music", 10.0f);
        Supplement sup2 = new Supplement("Sports", 20.0f);
        Supplement sup3 = new Supplement("Entertainment", 30.0f);
        Supplement sup4 = new Supplement("Art", 40.0f);

        // add supplements to magazine
        mag.addSupplement(sup1);
        mag.addSupplement(sup2);
        mag.addSupplement(sup3);
        mag.addSupplement(sup4);

        // create a paying customer and an associate customer
        PayingCustomer pc1 = new PayingCustomer("Mark",
                "mark@example.com", "c");
        sub.addSupplement(pc1.getCustomerId(), sup1, mag);
        sub.addSupplement(pc1.getCustomerId(), sup2, mag);

        PayingCustomer pc2 = new PayingCustomer("Sam",
                "sam@example.com", "d");
        sub.addSupplement(pc2.getCustomerId(), sup2, mag);
        sub.addSupplement(pc2.getCustomerId(), sup4, mag);

        AssociateCustomer ac1 = new AssociateCustomer("john",
                "john@example.com");
        sub.addSupplement(ac1.getCustomerId(), sup3, mag);
        sub.addSupplement(ac1.getCustomerId(), sup4, mag);
        ac1.setPayingCustomer(pc1);

        AssociateCustomer ac2 = new AssociateCustomer("jane",
                "jane@example.com");
        sub.addSupplement(ac2.getCustomerId(), sup1, mag);
        sub.addSupplement(ac2.getCustomerId(), sup3, mag);
        ac2.setPayingCustomer(pc1);

        AssociateCustomer ac3 = new AssociateCustomer("mary",
                "mary@example.com");
        sub.addSupplement(ac3.getCustomerId(), sup2, mag);
        sub.addSupplement(ac3.getCustomerId(), sup3, mag);
        ac3.setPayingCustomer(pc2);

        AssociateCustomer ac4 = new AssociateCustomer("joe",
                "joe@example.com");
        sub.addSupplement(ac4.getCustomerId(), sup1, mag);
        sub.addSupplement(ac4.getCustomerId(), sup2, mag);
        sub.addSupplement(ac4.getCustomerId(), sup3, mag);
        ac4.setPayingCustomer(pc2);

        // add customer to magazine
        mag.addCustomer(pc1);
        mag.addCustomer(pc2);
        mag.addCustomer(ac1);
        mag.addCustomer(ac2);
        mag.addCustomer(ac3);
        mag.addCustomer(ac4);

        //Calculation.getMonthlyCostEmail(mag, sub);
        Calculation.getWeeklyEmail(mag, sub);
    }

}
