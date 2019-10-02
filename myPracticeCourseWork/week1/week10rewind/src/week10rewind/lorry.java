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
public class lorry extends vehicle{
    
    //Constructor
    public lorry(String regIn, double weightIn){
        super(regIn,weightIn);
    }
    
    //Method
    @Override
    public double calculateFee(){
        if(weight>8000){
            return 15.00;
        }
        return 10.00;
    }
}
