/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.web;

import org.solent.com504.tca2019.model.ServiceFactory;
import org.solent.com504.tca2019.service.ServiceFactoryImpl;

/**
 *
 * @author cgallen
 */
public class WebObjectFactory {

    final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    
    private static final String DATA_FILE_LOCATION = TMP_DIR+"/store/app-data.xml";

    private static ServiceFactory serviceFactory = null;

    public static ServiceFactory getServiceFactory() {

        if (serviceFactory == null) {
            synchronized (WebObjectFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl(DATA_FILE_LOCATION);
                }
            }
        }
        return serviceFactory;
    }
}
