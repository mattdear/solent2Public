package org.solent.com504.project.model.service;

public interface ServiceFacade {
    
    public String getHeartbeat();
    
    public boolean arrived(String user, String location);
    
}
