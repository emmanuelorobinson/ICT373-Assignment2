/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;

/**
 * @title Supplement
 * @description This class is used to create a supplement object.
 * @filename Supplement.java
 * @version 1.0
 * @date 04/03/2020
 * @author Emmanuel Ejakpomewhe
 */
public class Supplement implements Serializable {

    private String name;
    private float cost;

    /**
     * Non-default constructor for Supplement
     * 
     * @param name
     * @param cost
     */
    public Supplement(String name, float cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Default constructor for Supplement
     */
    public Supplement() {
        this.name = "invlaid";
        this.cost = 0;
    }

    /**
     * Getter for supplement name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for supplement name
     * 
     * @param name
     * @return true if name is valid
     */
    public boolean setName(String name) {

        if (name.length() > 0) {
            this.name = name.toLowerCase();
            return true;
        }
        return false;

    }

    /**
     * Getter for supplement cost
     * 
     * @return cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * Setter for supplement cost
     * 
     * @param cost
     * @return true if cost is valid
     */
    public boolean setCost(float cost) {

        if (cost >= 0) {
            this.cost = cost;
            return true;
        }
        return false;
    }

    // gets supplement name and cost multiplied by numOfWeek

    /**
     * Gets supplement name and cost multiplied by numOfWeek
     * 
     * @param numOfWeek
     * @return name and cost multiplied by numOfWeek
     */
    public String getCostDetails(int numOfWeek) {
        String str = "";

        if (numOfWeek == 1) {
            str = " :: ";
        } else if (numOfWeek == 4) {
            str = " :: Month-";
        }

        if (this.name.equals("")) {
            return ("No supplements added");
        } else {
            return ("--->" + this.name + str + "Sum: $" + (this.cost * numOfWeek));
        }

    }
}
