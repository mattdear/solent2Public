/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.service.test;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ServiceFacade;
import org.solent.com504.tca2019.model.ServiceFactory;
import org.solent.com504.tca2019.service.ServiceFactoryImpl;

/**
 *
 * @author cgallen
 */
public class ServiceFacadeImplTest {

    public static final String TEST_DATA_FILE = "./target/testfile.xml";

    // Only some basic tests as most tests already done in ItemDAO tests
    @Test
    public void simpleServiceFacadeTest() {

        // use service factory to get access to service
        ServiceFactory serviceFactory = new ServiceFactoryImpl(TEST_DATA_FILE);
        assertNotNull(serviceFactory);

        ServiceFacade serviceFacade = serviceFactory.getServiceFacade();
        assertNotNull(serviceFacade);
        
        // clear file before anything else
        serviceFacade.deleteAllEntities();

        Item item = new Item();
        item.setSku("testFieldA");

        serviceFacade.createItem(item);
        List<Item> retrievedEntities = serviceFacade.retrieveMatchingEntities(item);

        assertEquals(1, retrievedEntities.size());
    }
}
