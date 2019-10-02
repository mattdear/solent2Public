/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import org.junit.Test;
import static org.junit.Assert.*;
import week10rewind.car;
import week10rewind.lorry;
import week10rewind.motorbike;
import week10rewind.vehicle;

/**
 *
 * @author blnkspc
 */
public class vehicletest {
    
    @Test
    public void bikeTest(){
        vehicle m1 = new motorbike("a",1000);
        m1.calculateFee();
    }
    
    @Test
    public void carTest(){
        vehicle m2 = new car("b",3001);
        m2.calculateFee();
    }
    
    @Test
    public void lorryTest(){
        vehicle m3 = new lorry("c",8000);
        m3.calculateFee();
    }
    
}
