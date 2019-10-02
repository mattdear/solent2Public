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
public class bridge {
    
    //Attributes
    private vehicle[] space;
    
    //Constructor
    public bridge(){
        space = new vehicle[20];
    }
    
    //Methods
    //Add vehicle
    public boolean addVehicle(vehicle vehicleIn){
        for(int i=0;i<space.length;i++){
            if(space[i]==null){
                space[i]=vehicleIn;
                return true;
            }
        }
        return false;
    }
    
    //Remove vehicle
    public boolean removeVehicle(String regIn){
        for(int i=0;i<space.length;i++){
            if(space[i]!=null){
                if(space[i].getReg().equals(regIn)){
                    space[i]=null;
                    return true;
                }
            }
        }
        return false;
    }
    
    //Total weight
    public double calcTotalWeight(){
        double tally=0;
        for(int i=0;i<space.length;i++){
            if(space[i]!=null){
                tally=tally+space[i].getWeight();
            }
        }
        return tally;
    }
}
