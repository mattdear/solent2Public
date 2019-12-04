/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.web.rest.client.test.manual;

import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ReplyMessage;
import org.solent.com504.tca2019.web.rest.client.RestClientJerseyImpl;

/**
 *
 * @author cgallen
 */
public class RestClientJerseyImplTest {

    String baseUrl = "http://localhost:8084/";

    MediaType mediaType = MediaType.APPLICATION_XML_TYPE;

    @Test
    public void restClientRetreiveTest() {

        RestClientJerseyImpl restClient = new RestClientJerseyImpl(baseUrl, mediaType);

        // try to retreive an unknown item
        ReplyMessage replyMessage = restClient.retrieveItem(Integer.SIZE);
        assertNotNull(replyMessage);
        assertTrue(replyMessage.getItemList().getItems().isEmpty());

        // try to retrieve item with id 1
        ReplyMessage replyMessage2 = restClient.retrieveItem(1);
        assertNotNull(replyMessage2);
        
        List<Item> items = replyMessage2.getItemList().getItems();
        assertNotNull(items);
        
        System.out.println("Received "+items.size()+" items");
        for(Item item: items){
            System.out.println("      Itemlist Item: " + item);
        }
        
        //assertEquals(1, replyMessage2.getItemList().getEntities().size());

        Item item = replyMessage2.getItemList().getItems().get(0);
        System.out.println("Received Item: " + item);

    }

    @Test
    public void restClientRetreiveTemplateTest() {

        RestClientJerseyImpl restClient = new RestClientJerseyImpl(baseUrl, mediaType);

        Item itemTempate = new Item();
        itemTempate.setSku("abcd");

        // try to retreive an unknown item
        ReplyMessage replyMessage = restClient.retrieveMatchingItems(itemTempate);
        assertNotNull(replyMessage);

        List<Item> itemList =  replyMessage.getItemList().getItems();
        System.out.println("Received "
                + itemList.size()
                + " Entities");
        
       for(Item e: itemList){
           System.out.println("   "+ e);
       }
        
    }
}
