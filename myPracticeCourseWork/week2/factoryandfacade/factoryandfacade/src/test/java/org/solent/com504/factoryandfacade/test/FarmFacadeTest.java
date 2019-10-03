package org.solent.com504.factoryandfacade.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import org.solent.com504.factoryandfacade.model.Animal;
import org.solent.com504.factoryandfacade.model.AnimalObjectFactory;
import org.solent.com504.factoryandfacade.model.FarmFacade;

/**
 *
 * @author cgallen
 */
public class FarmFacadeTest {

    @Test
    public void FarmFacadeTest() {

        FarmFacade farmFacade = AnimalObjectFactory.createFarmFacade();
        assertNotNull(farmFacade);
       
        // WHAT TESTS WOULD YOU CREATE HERE TO SET UP AND TEST THE FARM FACADE?
    
        farmFacade.addDog("Josh");
        farmFacade.addCat("Matthew");
        farmFacade.addCow("Eric");
        farmFacade.addDuck("Daisy");
        List<Animal> testList; 
        testList = farmFacade.getAllAnimals();
        
        assertEquals("Josh",testList.get(0).getName());
        assertEquals("Matthew",testList.get(1).getName());
        assertEquals("Eric",testList.get(2).getName());
        assertEquals("Daisy",testList.get(3).getName());
        
        assertEquals("Quak!",testList.get(3).getSound());
    }
}