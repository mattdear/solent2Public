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
public class car extends vehicle{
    
    //Constructor
    public car(String regIn, double weightIn){
        super(regIn,weightIn);
    }
    
    //Method
    @Override
    public double calculateFee(){
        if(weight>1689 && weight<1790) {
            return 5.10;
	}
	else if(weight>1789 && weight<1890) {
            return 5.20;
	}
	else if(weight>1889 && weight<1990) {
            return 5.30;
        }
	else if(weight>1989 && weight<2090) {
            return 5.40;
	}
	else if(weight>2089 && weight<2190) {
            return 5.50;
	}
	else if(weight>2189 && weight<2290) {
            return 5.60;
	}
	else if(weight>2289 && weight<2390) {
            return 5.70;
	}
	else if(weight>2389 && weight<2490) {
            return 5.80;
	}
	else if(weight>2489 && weight<2590) {
            return 5.90;
	}
	else if(weight>2589 && weight<2690) {
            return 6.00;
	}
	else if(weight>2689 && weight<2790) {
            return 6.10;
	}
	else if(weight>2789 && weight<2890) {
            return 6.20;
	}
	else if(weight>2889 && weight<3001) {
            return 6.30;
	}
        return 5.00;
    } 
}
