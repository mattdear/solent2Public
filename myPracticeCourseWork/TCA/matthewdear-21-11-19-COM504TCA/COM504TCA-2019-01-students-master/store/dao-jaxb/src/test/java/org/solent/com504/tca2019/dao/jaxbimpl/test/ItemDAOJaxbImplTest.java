/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.dao.jaxbimpl.test;

import java.io.File;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com504.tca2019.dao.jaxbimpl.ItemDAOJaxbImpl;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ItemDAO;

/**
 * tests for itemDao.createItem(item) itemDao.deleteItem(Id) itemDao.retrieveAllEntities() itemDao.retrieveItem(Id)
 * itemDao.retrieveMatchingEntites(itemTempate) itemDao.updateItem(item)
 *
 * @author cgallen
 */
public class ItemDAOJaxbImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDAOJaxbImplTest.class);

    public final String TEST_DATA_FILE_LOCATION = "target/testDaofile.xml";

    @Test
    public void testDestinationsDAOJaxb() {

        // delete test file at start of test
        File file = new File(TEST_DATA_FILE_LOCATION);
        file.delete();
        assertFalse(file.exists());

        // create dao
        ItemDAO itemDao = new ItemDAOJaxbImpl(TEST_DATA_FILE_LOCATION);

        // check that new file created
        assertTrue(file.exists());

        // check there are no entities
        assertTrue(itemDao.retrieveAllEntities().isEmpty());

        // add a 3 entities
        int ITEM_NUMBER = 4;
        for (int entityId = 0; entityId < ITEM_NUMBER; entityId++) {
            Item item = new Item();
            item.setSku("sku_" + entityId);
            item.setDescription("description_" + entityId);;
            item.setPrice(Double.valueOf(entityId));
            item.setQuantity(entityId+1);

            LOG.debug("adding item:" + item);
            Item e = itemDao.createItem(item);
            assertNotNull(e);
        }

        // check 3 entities added
        assertTrue(ITEM_NUMBER == itemDao.retrieveAllEntities().size());

        // check return false for delete unknown item
        assertFalse(itemDao.deleteItem(Integer.SIZE));

        // find an item to delete
        List<Item> elist = itemDao.retrieveAllEntities();
        Integer idToDelete = elist.get(1).getId();
        LOG.debug("deleting  item:" + idToDelete);

        // check found and deleted
        assertTrue(itemDao.deleteItem(idToDelete));

        // check no longer found after deletion
        assertNull(itemDao.retrieveItem(idToDelete));

        // check entities size decremeted
        List<Item> elist2 = itemDao.retrieveAllEntities();
        assertTrue(ITEM_NUMBER - 1 == elist2.size());

        // update item
        Item itemToUpdate = elist2.get(1);
        LOG.debug("updating item: " + itemToUpdate);

        // add 3 newProperties for item
        itemToUpdate.setSku("sku_Update");
        itemToUpdate.setDescription("description_Update");
        itemToUpdate.setPrice(null); // do not update field C
        LOG.debug("update template: " + itemToUpdate);

        Item updatedItem = itemDao.updateItem(itemToUpdate);
        LOG.debug("updated item: " + updatedItem);
        assertNotNull(updatedItem);

        // check item updated
        Item retrievedItem = itemDao.retrieveItem(updatedItem.getId());
        LOG.debug("retreived item: " + retrievedItem);
        assertEquals(itemToUpdate.getSku(), retrievedItem.getSku());
        assertEquals(itemToUpdate.getDescription(), retrievedItem.getDescription());
        assertNotEquals(itemToUpdate.getPrice(), retrievedItem.getPrice());

        // test retrieve matching entities
        List<Item> itemList = itemDao.retrieveAllEntities();
        Item searchfor = itemList.get(2);
        LOG.debug("searching for: " + searchfor);

        Item template = new Item();
        template.setDescription(searchfor.getDescription());
        LOG.debug("using template : " + template);

        List<Item> retrievedList = itemDao.retrieveMatchingEntities(template);
        assertEquals(1, retrievedList.size());

        LOG.debug("found : " + retrievedList.get(0));
        
        assertEquals(searchfor, retrievedList.get(0));

    }

}
