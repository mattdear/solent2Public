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
public class motorbike extends vehicle{
    
//Constructor
    public motorbike(String regIn, double weightIn){
        super(regIn,weightIn);
    }
    
    //Methods
    @Override
    public double calculateFee(){
        return 3.00;
    }
}
