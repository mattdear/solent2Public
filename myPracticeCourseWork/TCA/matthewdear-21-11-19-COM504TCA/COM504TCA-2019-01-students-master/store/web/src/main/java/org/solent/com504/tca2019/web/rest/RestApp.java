package org.solent.com504.tca2019.web.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApp extends ResourceConfig {

    public RestApp() {
        packages("org.solent.com504.tca2019.web.rest");
    }
}
