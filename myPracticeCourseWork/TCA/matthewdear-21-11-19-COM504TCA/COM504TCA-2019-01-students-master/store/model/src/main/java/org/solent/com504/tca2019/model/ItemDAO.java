package org.solent.com504.tca2019.model;

import java.util.List;

public interface ItemDAO {

    public Item createItem(Item item);

    public boolean deleteItem(Integer id);

    public void deleteAllEntities();

    public Item retrieveItem(Integer id);

    public List<Item> retrieveAllEntities();

    public List<Item> retrieveMatchingEntities(Item itemTempate);

    public Item updateItem(Item item);
}
