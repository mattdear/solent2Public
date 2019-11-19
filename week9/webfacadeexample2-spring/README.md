
# Project using Spring MVC

This version of the project is beginning to introduce SpringMVC as a model view controller for the JSP's.
This project illustates a partial migration to Spring where elements of the legacy code still remain but are integrated with the new code using Spring.
This represents a realistic situation where new technology is introduced in controlled stages and the existing code is 'refactored' to use the new framework.

In this example, we have kept the original JSP's and Object Factorys but have introduced new Spring MVC based JSP's running along side. 
This means that you can compare the old and new code to see how the new functionality implements the same features as the old.
The old JSP's continue to work along side the new.

## Changes in this code

The majority of the changes in this example are in the web module as shown below 
```
web
   src
      main
         java
            org.solent.com504.factoryandfacade.impl.rest
                RestApp.java
                RestService.java      // NEW ANNOTATIONS FOR BEANS
            org.solent.com504.factoryandfacade.impl.web
                HelloWorld.java       // NEW - SIMPLE CLASS TO HELP EXPLAIN/DEBUG APPLCATIONCONTEXTS- this just logs a message when created.
                ViewController.java   // NEW - SPRING MVC CONTROLLER
                WebObjectFactory.java
      resources
         applicationContext.xml       // NEW - ROOT APPLICATION CONTEXT
         log4j2.xml
         mvc-dispatcher-servelet.xml // NEW - CHILD APPLICATION CONTEXT FOR MVC DISPATCHER
   webapp
      WEB-INF
         views  // NEW JSP'S WHICH ARE ONLY VIEWS WITH NO BUSINESS LOGIC
            farmlist.jsp
            reviewAnimal.jsp
         web.xml // CONFIGURATION TO SET UP MVC DISPATCHER SERVLET
      images
         Village-Farm.svg
      farm.jsp
      farm2.jsp
      index.jsp
      viewAnimal.jsp


```
The project's pom.xml files have also been updated to include some new depencencies.

## Spring application context

The first major change is the introduction of a spring application context which is intended to replace all references to ObjectFactories in the new code. 
When we remove the old code, we will remove all of the object factories but in the interim, the spring code uses the object factories to access the underlying lbraries.

```
applicationContext.xml
...

    <!-- this allows annotations to be picked up by configuration -->
    <context:annotation-config/>
    <context:component-scan base-package="org.solent.com504.factoryandfacade.impl.rest" />
     
    <!-- this allows a factory to generate a singleton bean -->
    <bean id="serviceFacade" class="org.solent.com504.factoryandfacade.impl.web.WebObjectFactory" factory-method="getServiceFacade"></bean> 
     
    <!-- this just activates and destroys a bean as an example -->
    <bean id="helloWorld" class="org.solent.com504.factoryandfacade.impl.web.HelloWorld" init-method="init" destroy-method="destroy">
        <property name="message" value="main applicationContext" />
    </bean>
```
The first lines in the file cause spring to scan all of the java files in the ```org.solent.com504.factoryandfacade.impl.rest``` package.

The scanner looks for spring annotations such as @Component and @Resource(name="serviceFacade").

When a @Component annotated class is identified, the @Resource(name="serviceFacade") causes Spring to antomatically 'inject' the serviceFacade bean.

### Jersey
The version of jersey has been updated to allow it to work along side Spring MVC. 
The pom.xml depencency jars for the later version of Jersey have been changed but the code is substantially the same.
In the RestService.java @Component causes Spring to initialise and register an instance of the RestService.

The @Resource(name="serviceFacade") causes Spring to antomatically 'inject' the serviceFacade bean. This means that the RestService class no longer needs to reference the WebObjectFactory.

```
@Component // component allows resource to be picked up
@Path("/farmService")
public class RestService {

    // SETS UP LOGGING 
    // note that log name will be org.solent.com504.factoryandfacade.impl.rest.RestService
    final static Logger LOG = LogManager.getLogger(RestService.class);

    // This serviceFacade object is automacally injected by Spring
    @Resource(name="serviceFacade")
    FarmFacade serviceFacade = null;   
    
```

The definition for the serviceFacade bean is in the applicationContext.xml file.
Because we are still using the legacy WebObjectFactory in the old jsp code, we have a bean definition in the applicationContext.xml which uses the same WebObjectFactory. You will see that this calls the getServiceFacade() method in the WebObjectFactory to return a reference to a singleton serviceFacade object.

Please compare 
[week9 RestService.java](../../week9/webfacadeexample2-spring/web/src/main/java/org/solent/com504/factoryandfacade/impl/rest/RestService.java ) 
with 
[week6 RestService.java](../../week6/webfacadeexample2/web/src/main/java/org/solent/com504/factoryandfacade/impl/rest/RestService.java ) 

You will see that with the WebObjectFatory removed, the code is simplified in the RestService and the WebObjectFactory is no longer needed.

## Spring MVC
A new class, [ViewController.java](../../week9/webfacadeexample2-spring/web/src/main/java/org/solent/com504/factoryandfacade/impl/web/ViewController.java ) has been introduced which acts as the Controller in the Model View Controller implementation.

An excerpt of this class is shown below. 
The @Controller annotation identifies this class as a controller in the MVC.

@RequestMapping("/mvc") means that the controller will process URL's with /mvc in the classpath.
This is very similar to the @Path annotation in JAX-RS

The @Resource(name = "serviceFacade") annotation injects the farmFacade service bean created by the application context (as discussed above).

The @RequestMapping("/farmhome") annotation maps the call to the farmhome method. Again this is very like JAX-RS.

```
@Controller
@RequestMapping("/mvc")
public class ViewController {

    final static Logger LOG = LogManager.getLogger(ViewController.class);

    {
        LOG.debug("ViewController created");
    }

    // This serviceFacade object is injected by Spring
    @Resource(name = "serviceFacade")
    FarmFacade serviceFacade = null;

    @RequestMapping("/farmhome")
    public String farmhome(Model m,
            @RequestParam(value = "animalName", required = false) String animalName,
            @RequestParam(value = "animalType", required = false) String animalType) {

        LOG.debug("farmhome called animalType=" + animalType + " animalName=" + animalName);

        List<Animal> animalsList = serviceFacade.getAllAnimals();
        List<String> supportedAnimalTypes = serviceFacade.getSupportedAnimalTypes();
        m.addAttribute("animalsList", animalsList);
        m.addAttribute("supportedAnimalTypes", supportedAnimalTypes);

        // add error / response messages to page
        String errorMessage = "";
        String message = "";
        m.addAttribute("errorMessage", errorMessage);
        m.addAttribute("message", message);

        // render view with jsp
        return "farmlist";
    }
```













