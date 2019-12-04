/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.tca2019.service;

import org.solent.com504.tca2019.dao.jaxbimpl.ItemDAOJaxbImpl;
import org.solent.com504.tca2019.model.ItemDAO;
import org.solent.com504.tca2019.model.ServiceFacade;
import org.solent.com504.tca2019.model.ServiceFactory;

/**
 *
 * @author cgallen
 */
public class ServiceFactoryImpl implements ServiceFactory {

    ServiceFacade serviceFacade = null;

    String dataFileUri = null;

    public ServiceFactoryImpl(String dataFileUri) {
        if (dataFileUri == null) {
            throw new IllegalArgumentException("dataFileUri must not be null");
        }
        
        ItemDAO itemDao = new ItemDAOJaxbImpl(dataFileUri);
        ServiceFacadeImpl serviceFacadeImpl = new ServiceFacadeImpl();
        serviceFacadeImpl.setItemDAO(itemDao);
        serviceFacade = serviceFacadeImpl;
        
    }

    @Override
    public ServiceFacade getServiceFacade() {
        return serviceFacade;
    }

}
