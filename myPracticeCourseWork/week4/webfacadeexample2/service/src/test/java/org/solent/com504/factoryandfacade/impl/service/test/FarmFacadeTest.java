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
    assertEquals(tempAnimal.getAnimalType(),"Cow");
    }
    
    @Test
    public void addCat(){
    FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
    Animal tempAnimal = farmFacade.addAnimal("Cat", "Matt");
    assertEquals(tempAnimal.getName(),"Matt");
    assertEquals(tempAnimal.getAnimalType(),"Cat");
    }
    
    @Test
    public void addDog(){
    FarmFacade farmFacade = serviceObjectFactory.getFarmFacade();
    Animal tempAnimal = farmFacade.addAnimal("Dog", "James");
    assertEquals(tempAnimal.getName(),"James");
    assertEquals(tempAnimal.getAnimalType(),"Dog");
    }
    
}
