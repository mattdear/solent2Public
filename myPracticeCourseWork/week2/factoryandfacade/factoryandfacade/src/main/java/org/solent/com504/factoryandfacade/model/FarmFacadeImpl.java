/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.factoryandfacade.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author blnkspc
 */
public class FarmFacadeImpl implements FarmFacade {
    
    List<Animal> allAnimals = new ArrayList();
    
    @Override
    public List<Animal> getAllAnimals(){
        List<Animal> killList = new ArrayList();
        for (Animal a : allAnimals) {
           killList.add(a);                 
        }
        return killList;
    }
    
    @Override
    public void addDog(String name){
       Animal a = AnimalObjectFactory.createDog();
       a.setName(name);
       allAnimals.add(a);
    }
    
    @Override
    public void addCat(String name){
        Animal a = AnimalObjectFactory.createCat();
        a.setName(name);
        allAnimals.add(a);
    }
    
    @Override
    public void addCow(String name){
        Animal a = AnimalObjectFactory.createCow();
        a.setName(name);
        allAnimals.add(a);
    }
}
