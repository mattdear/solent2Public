/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cgallen
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemList {

    // only used by persistance layer
    private Integer lastItemId = null;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> entities) {
        this.items = entities;
    }
    
    
    public Integer getLastItemId() {
        return lastItemId;
    }

    public void setLastItemId(Integer lastItemId) {
        this.lastItemId = lastItemId;
    }

    @Override
    public String toString() {
        return "ItemList{" + "lastItemId=" + lastItemId + ", items=" + items + '}';
    }

}
