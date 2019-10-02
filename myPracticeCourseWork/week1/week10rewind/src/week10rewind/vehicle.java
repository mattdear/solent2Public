/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week10rewind;

/**
 *
 * @author blnkspc
 */
public abstract class vehicle{
    
    //Attributes
    protected double weight;
    protected String reg;
    
    //Constructor
    public vehicle(String regIn, double weightIn){
        reg = regIn;
        weight = weightIn;
    }
    
    //Methods
    public abstract double calculateFee();
    
    //Getters
    public String getReg(){
        return reg;
    }
    
    public double getWeight(){
        return weight;
    }
}
