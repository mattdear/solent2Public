package org.solent.com504.tca2019.model;

public interface RestInterface {

    public ReplyMessage retrieveMatchingItems(Item itemTempate);
    
    public ReplyMessage retrieveItem(Integer id);
    
}
