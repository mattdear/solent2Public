<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="org.solent.com504.factoryandfacade.model.Animal"%>
<%@page import="org.solent.com504.factoryandfacade.model.FarmFacade"%>
<%@page import="org.solent.com504.factoryandfacade.model.FarmObjectFactory"%>
<%@page import="org.solent.com504.factoryandfacade.impl.FarmObjectFactoryImpl"%>
<%@page import="org.solent.com504.factoryandfacade.impl.FarmFacadeImpl"%>

<%

    FarmFacade farmFacade = (FarmFacade) session.getAttribute("farmFacade");

    // synchronised block to prevent multiple creations of factory
    if (farmFacade == null) {
        synchronized (this) {
            if (farmFacade == null) {
                FarmObjectFactory farmobjectFactory = new FarmObjectFactoryImpl();
                farmFacade = farmobjectFactory.createFarmFacade();
                session.setAttribute("farmFacade", farmFacade);

                List<String> supportedAnimalTypes = farmobjectFactory.getSupportedAnimalTypes();
                session.setAttribute("supportedAnimalTypes", supportedAnimalTypes);
            }
        }
    }

    List<String> supportedAnimalTypes = (List<String>) session.getAttribute("supportedAnimalTypes");

    // access ing request parameters
    String animalNameStr = request.getParameter("animalName");
    String animalTypeStr = request.getParameter("animalType");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page Farm</title>
    </head>

    <!-- works with http://localhost:8084/basicfacadeweb/example2.jsp?animalType=emue&animalName=Fred -->
    <%        if (animalNameStr != null || animalTypeStr != null) {
            farmFacade.addAnimal(animalTypeStr, animalNameStr);
    %>
    <p>create animal type= <%=animalTypeStr%> name= <%=animalNameStr%></p>

    <%}
    %>
    <body>
        <p><h2>Page for Farm</h2></p>
    <p><h3>Supported Animal Types</h3></p>
<table>
    <% for (String animalType : supportedAnimalTypes) {%>
    <tr>
    <form action="./example2.jsp" method="get">
        Name: <input type="text" name="animalName"><input type="hidden" name="animalType" value="<%=animalType%>"><button type="submit">Create <%=animalType%></button><br><br>
    </form>
</tr>
<%
    }
%>
</table> 

<p><h3>Animals on Farm</h3></p>
<table>
    <tr>
        <td>Type</td>
        <td>Name</td>
        <td>Sound</td>
    </tr>
    <% for (Animal animal : farmFacade.getAllAnimals()) {%>
    <tr>
        <td><%=animal.getAnimalType()%></td>
        <td><%=animal.getName()%></td>
        <td><%=animal.getSound()%></td>
    </tr>
    <%
        }
    %>
</table> 

</body>
</html>
