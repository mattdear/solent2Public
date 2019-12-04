<%-- 
    Document   : ListItems
    Created on : Nov 30, 2018, 11:17:02 PM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.solent.com504.tca2019.web.WebObjectFactory"%>
<%@page import="org.solent.com504.tca2019.model.ServiceFactory"%>
<%@page import="org.solent.com504.tca2019.model.ServiceFacade"%>
<%@page import="org.solent.com504.tca2019.model.Item"%>

<%

    ServiceFacade serviceFacade = (ServiceFacade) session.getAttribute("serviceFacade");

    // If the user session has no bankApi, create a new one
    if (serviceFacade == null) {
        ServiceFactory serviceFactory = WebObjectFactory.getServiceFactory();
        serviceFacade = serviceFactory.getServiceFacade();
        session.setAttribute("ServiceFacade", serviceFacade);
    }

    // get request values
    String action = (String) request.getParameter("action");
    String itemIdReq = (String) request.getParameter("itemId");
    String itemSkuReq = (String) request.getParameter("sku");
    String itemDescriptionReq = (String) request.getParameter("description");
    String itemPriceReq = (String) request.getParameter("price");
    String itemQuantityReq = (String) request.getParameter("quantity");

    String errorMessage = "";
    if ("deleteItem".equals(action)) {
        try {
            Integer itemId = Integer.parseInt(itemIdReq);
            serviceFacade.deleteItem(itemId);
        } catch (Exception e) {
            errorMessage = "problem deleting Item " + e.getMessage();
        }
    } else if ("modifyItem".equals(action)) {
        try {
            Integer itemId = Integer.parseInt(itemIdReq);
            Double itemPrice = Double.parseDouble(itemPriceReq);
            Integer itemQuantity = Integer.parseInt(itemQuantityReq);
            Item itemTemplate = new Item();
            itemTemplate.setId(itemId);
            itemTemplate.setSku(itemSkuReq);
            itemTemplate.setDescription(itemDescriptionReq);
            itemTemplate.setPrice(itemPrice);
            itemTemplate.setQuantity(itemQuantity);
            Item item = serviceFacade.updateItem(itemTemplate);
            if (item == null) {
                errorMessage = "problem modifying item. Could not find itemId " + itemId;
            }
        } catch (Exception e) {
            errorMessage = "problem modifying item " + e.getMessage();
        }
    } else if ("createItem".equals(action)) {
        try {
            Double itemPrice = Double.parseDouble(itemPriceReq);
            Integer itemQuantity = Integer.parseInt(itemQuantityReq);
            Item itemTemplate = new Item();
            itemTemplate.setSku(itemSkuReq);
            itemTemplate.setDescription(itemDescriptionReq);
            itemTemplate.setPrice(itemPrice);
            itemTemplate.setQuantity(itemQuantity);
            Item item = serviceFacade.createItem(itemTemplate);
            if (item == null) {
                errorMessage = "problem creating item. Service returned null ";
            }
        } catch (Exception e) {
            errorMessage = "problem creating item " + e.getMessage();
            //throw new RuntimeException(e);
        }
    }

    List<Item> itemList = serviceFacade.retrieveAllEntities();
    // PROVIDE CODE HERE TO CALCULATE THE TOTAL VALUE FOR THE WHOLE LIST
    Double total = 0.0;
    for(Item item : itemList){
    total = total + (item.getQuantity()*item.getPrice());
    }  

%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Item List</title>
    </head>
    <body>
        <!-- print error message if there is one -->
        <div style="color:red;"><%=errorMessage%></div>
        <h1>Item List</h1>
        <table>
            <tr>
                <th>id</th>
                <th>sku</th>
                <th>description</th>
                <th>price</th>
                <th>quantity</th>
                <th>sub total</th>
                <th></th>
            </tr>
            <%  for (Item item : itemList) {
                // ENTER CODE HERE TO CALCULATE ITEM SUB TOTAL FOR EACH ITEM
                Double subTotal = 0.0;
                subTotal = subTotal + (item.getPrice()*item.getQuantity());
            %>
            <tr>
                <td><%=item.getId()%></td> 
                <td><%=item.getSku()%></td> <!-- REPLACE TBD WITH CORRECT VALUES OF ITEM -->
                <td><%=item.getDescription()%></td> <!-- REPLACE TBD WITH CORRECT VALUES OF ITEM -->
                <td><%=item.getPrice()%></td> <!-- REPLACE TBD WITH CORRECT VALUES OF ITEM -->
                <td><%=item.getQuantity()%></td> <!-- REPLACE TBD WITH CORRECT VALUES OF ITEM -->
                <td><%=subTotal%></td> 
                <td>
                    <form action="AddOrModifyItem.jsp">
                        <input type="hidden" name="action" value="modifyItem">
                        <input type="hidden" name="itemId" value="<%=item.getId()%>">
                        <input type="submit" value="Modify Item">
                    </form>
                    <form action="ListItems.jsp">
                        <input type="hidden" name="action" value="deleteItem">
                        <input type="hidden" name="itemId" value="<%=item.getId()%>">
                        <input type="submit" value="Delete Item">
                    </form>
                        <!-- MODIFY CODE TO DELETE ITEM CORRECTLY -->
                </td>
            </tr>
            <% }%>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>TOTAL: </th>
                <th><%=total%></th> <!-- CALCULATE THE TOTAL FOR ALL VALUES IN THE STORE -->
            </tr>
        </table> 
        <BR>
        <form action="AddOrModifyItem.jsp">
            <input type="hidden" name="action" value="createItem">
            <input type="submit" value="Create Item">
        </form>
    </body>
</html>
