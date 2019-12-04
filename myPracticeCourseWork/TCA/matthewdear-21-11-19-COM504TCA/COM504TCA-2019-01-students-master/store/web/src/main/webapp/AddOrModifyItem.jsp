<%-- 
    Document   : AddOrModifyItem
    Created on : Nov 30, 2018, 11:17:38 PM
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

    Item item = null;
    Integer itemId = null;

    if ("modifyItem".equals(action)) {
        try {
            itemId = Integer.parseInt(itemIdReq);
            item = serviceFacade.retrieveItem(itemId);
        } catch (Exception e) {
            errorMessage = "problem finding item " + e.getMessage();
        }
    } else if ("createItem".equals(action)) {
        try {
            item = new Item();
            item.setPrice(0.0);
            item.setQuantity(0);
        } catch (Exception e) {
            errorMessage = "problem finding item " + e.getMessage();
        }
    } else {
        errorMessage = "cannot recognise action: " + action;
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Edit Item</title>
    </head>
    <body>
        <% if ("createItem".equals(action)) {
        %>
        <h1>Add New Item</h1>
        <% } else {%>
        <h1>Modify Item <%=itemId%></h1>
        <% }%>
        <form action="ListItems.jsp">
            <table>
                <tr>
                    <th>Field</th>
                    <th>Current Value</th>
                    <th>New Value</th>
                    <th>Quantity</th>
                </tr>
                <tr>
                    <td>Item Id</td>
                    <td><%=item.getId()%></td>
                    <td></td>
                </tr>
                <tr>
                    <td>sku</td>
                    <td><%=item.getSku()%></td>
                    <td><input type="text" name="sku" value ="<%=item.getSku()%>"></td>
                </tr>
                <tr>
                    <td>description</td>
                    <td><%=item.getDescription()%></td>
                    <td><input type="text" name="description" value ="<%=item.getDescription()%>"></td>
                </tr>
                <tr>
                    <td>price</td>
                    <td><%=item.getPrice()%></td>
                    <td><input type="text" name="price" value ="<%=item.getPrice()%>"></td>
                </tr>
                <tr>
                    <td>quantity</td>
                    <td><%=item.getQuantity()%></td>
                    <td><input type="text" name="quantity" value ="<%=item.getQuantity()%>"></td>
                </tr>
            </table> 
            <BR>
            <% if ("createItem".equals(action)) {
            %>
            <input type="hidden" name="action" value="createItem">
            <input type="hidden" name="itemId" value="<%=itemId%>">
            <input type="submit" value="Create New Item">
            <% } else if ("modifyItem".equals(action)) {
            %>
            <input type="hidden" name="action" value="modifyItem">
            <input type="hidden" name="itemId" value="<%=itemId%>">
            <input type="submit" value="Modify Item">
            <% }%>
        </form>
        <form action="ListItems.jsp">
            <input type="submit" value="Cancel and Return">
        </form>
    </body>
</html>
