/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.web.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com504.tca2019.model.Item;
import org.solent.com504.tca2019.model.ReplyMessage;
import org.solent.com504.tca2019.model.ServiceFacade;
import org.solent.com504.tca2019.web.WebObjectFactory;

/**
 *
 * @author cgallen
 */
@Path("/example")
public class ExampleProjectRestImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleProjectRestImpl.class);

    // http://localhost:8084/rest/example/retrievematching
    @POST
    @Path("/retrievematching")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response retrieveMatchingEntites(Item itemTemplate) {

        try {
            if (itemTemplate == null) {
                throw new IllegalArgumentException("itemTemplate request parameter must be set");
            }
            ReplyMessage replyMessage = new ReplyMessage();

            ServiceFacade serviceFacade = WebObjectFactory.getServiceFactory().getServiceFacade();
            List<Item> eList = serviceFacade.retrieveMatchingEntities(itemTemplate);

            LOG.debug("/retrievematching itemTemplate: " + itemTemplate 
                    + " found " + eList.size() + "entities");

            replyMessage.getItemList().getItems().addAll(eList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /retrievematching ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /retrievematching " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    // GET http://localhost:8084/rest/example/retrieve?id=1
    @GET
    @Path("/retrieve")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response retrieve(@QueryParam("id") Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("id request parameter must be set");
            }
            ReplyMessage replyMessage = new ReplyMessage();

            //TODO MODIFY CODE TO MAKE THIS REST SERVICE WORK
                      
            Item item = null;            
            ServiceFacade serviceFacade = WebObjectFactory.getServiceFactory().getServiceFacade();
            item = serviceFacade.retrieveItem(id);
            
            if (item != null) {
                LOG.debug("/retrieve id=" + id + " found item :" + item);
                replyMessage.getItemList().getItems().add(item);

                replyMessage.setCode(Response.Status.OK.getStatusCode());
                return Response.status(Response.Status.OK).entity(replyMessage).build();
            } else {
                LOG.debug("/retrieve id=" + id + " found no item :");
                replyMessage.setDebugMessage("/retrieve id=" + id + " found no item");
                replyMessage.setCode(Response.Status.OK.getStatusCode());
                return Response.status(Response.Status.OK).entity(replyMessage).build();
            }

        } catch (Exception ex) {
            LOG.error("error calling /retrieve ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /retrieve " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }

    }
}
