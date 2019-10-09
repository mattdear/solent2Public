<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="org.solent.com504.factoryandfacade.model.Animal"%>
<%@page import="org.solent.com504.factoryandfacade.model.FarmFacade"%>
<%@page import="org.solent.com504.factoryandfacade.model.FarmObjectFactory"%>
<%@page import="org.solent.com504.factoryandfacade.impl.FarmObjectFactoryImpl"%>

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
    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page Farm</title>
    </head>
    <body>
        <p>Page for Farm</p>
        <p>Supported Animal Types</p>
        <table>
            <% for (String animalType : supportedAnimalTypes) {%>
            <tr>
                <form>
                    <button type="button" value="<%=animalType%>">Create <%=animalType%></button><input type="text"></input> 
                </form>
            </tr>
            <%
                }
            %>
        </table> 

        <p>Animals on Farm</p>
        <table>
            <% for (Animal farmAnimal : animalsOnFarm) {%>
            <tr>
                <th>Type</th>
                <td><%=farmAnimal.getAnimalType()%></td>
                <th>Name</th>
                <td><%=farmAnimal.getName()%>></td>
                <th>Sound</th>
                <td><%farmAnimal.getSound();%></td>
            </tr>
            <%
                }
            %>
            
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
