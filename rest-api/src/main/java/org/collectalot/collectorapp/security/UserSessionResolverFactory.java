package org.collectalot.collectorapp.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@SessionScoped
public class UserSessionResolverFactory implements Serializable { 
    @Produces @PreferredUserSessionResolver
    public UserSessionResolver getUserSessionResolver(){
        return new HarcodedUserSessionResolver();
    }
}
