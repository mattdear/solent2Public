/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.factoryandfacade.impl.service.test;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.solent.com504.factoryandfacade.impl.service.ServiceObjectFactoryImpl;
import org.solent.com504.factoryandfacade.model.dto.Animal;
import org.solent.com504.factoryandfacade.model.service.ServiceObjectFactory;

import org.solent.com504.factoryandfacade.model.service.FarmFacade;

/**
 *
 * @author gallenc
 */
public class FarmFacadeTest {
    
    private ServiceObjectFactory  serviceObjectFactory = null;
    
    @Before
    public void init(){
    serviceObjectFactory = new ServiceObjectFactoryImpl();
    }
    
    @Test
    public void addCow(){
    FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
    Animal tempAnimal = farmFacade.addAnimal("Cow", "Josh");
    assertEquals(tempAnimal.getName(),"Josh");
    assertEquals(tempAnimal.getAnimalType().getType(),"Cow");
    }
    
    @Test
    public void addCat(){
    FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
    Animal tempAnimal = farmFacade.addAnimal("Cat", "Matt");
    assertEquals(tempAnimal.getName(),"Matt");
    assertEquals(tempAnimal.getAnimalType().getType(),"Cat");
    }
    
    @Test
    public void addDog(){
    FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
    Animal tempAnimal = farmFacade.addAnimal("Dog", "James");
    assertEquals(tempAnimal.getName(),"James");
    assertEquals(tempAnimal.getAnimalType().getType(),"Dog");
    }
    
    @Test
    public void addListAndRemoveAnimals(){
        FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
        Animal tempAnimal1 = farmFacade.addAnimal("Dog", "James");
        Animal tempAnimal2 = farmFacade.addAnimal("Cat", "Matt");
        Animal tempAnimal3 = farmFacade.addAnimal("Cow", "Josh");

        List<Animal> tempList = farmFacade.getAllAnimals();
        Animal tempAnimal4 = tempList.get(0);
        Animal tempAnimal5 = tempList.get(1);
        Animal tempAnimal6 = tempList.get(2);
        
        assertFalse(tempList.isEmpty());
        
        assertEquals(tempAnimal4.getName(), "James");
        assertEquals(tempAnimal5.getName(), "Matt");
        assertEquals(tempAnimal6.getName(), "Josh");
        
        assertEquals(tempAnimal4.getAnimalType().getSound(),"woof");
        assertEquals(tempAnimal5.getAnimalType().getSound(),"meow");
        assertEquals(tempAnimal6.getAnimalType().getSound(),"moo");
        
        assertEquals(tempAnimal4.getAnimalType().getType() ,"Dog");
        assertEquals(tempAnimal5.getAnimalType().getType() ,"Cat");
        assertEquals(tempAnimal6.getAnimalType().getType(),"Cow");
        
        assertTrue(farmFacade.removeAnimal("James"));
        assertTrue(farmFacade.removeAnimal("Matt"));
        assertTrue(farmFacade.removeAnimal("Josh"));
    }
}
