/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.service;

import java.util.List;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ItemDAO;
import org.solent.com504.tca2019.model.ServiceFacade;

/**
 *
 * @author cgallen
 */
public class ServiceFacadeImpl implements ServiceFacade {
    
    ItemDAO itemDAO = null;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void deleteAllEntities() {
       itemDAO.deleteAllEntities();
    }

    @Override
    public Item createItem(Item item) {
        return itemDAO.createItem(item);
    }

    @Override
    public boolean deleteItem(Integer id) {
        return itemDAO.deleteItem(id);
    }

    @Override
    public Item retrieveItem(Integer id) {
        return itemDAO.retrieveItem(id);
    }

    @Override
    public List<Item> retrieveAllEntities() {
        return itemDAO.retrieveAllEntities();
    }

    @Override
    public List<Item> retrieveMatchingEntities(Item itemTempate) {
        return itemDAO.retrieveMatchingEntities(itemTempate);
    }

    @Override
    public Item updateItem(Item item) {
        return itemDAO.updateItem(item);
    }
    
}
