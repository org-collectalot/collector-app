package org.collectalot.collectorapp.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
public class TitlePartRestIntegrationTest {

    @Test
    @RunAsClient
    public void testGetTitlePartFound() {
        Client client = ClientBuilder.newClient();
        //test finding an item
        WebTarget target = client.target("http://localhost:8080")
                .path("rest").path("title-part").path("2");

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
        String replyString = response.readEntity(String.class);
        assertEquals("{\"id\":2,\"parentId\":null,\"text\":\"Lucky Luke\",\"version\":1,\"deleted\":false}", replyString);
    }
    @Test
    @RunAsClient
    public void testGetTitlePartNotFound() {
        Client client = ClientBuilder.newClient();
        //test not finding an item
        WebTarget target = client.target("http://localhost:8080")
                .path("rest").path("title-part").path("99");

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(204, response.getStatus());

    }
    @Deployment
    public static Archive<?> createTestArchive() {
    	  return ShrinkWrap
    	    .create(WebArchive.class, "TestTitlePartDBBackend.war")
    	    .addPackage(org.collectalot.collectorapp.db.TitlePartDBBackend.class.getPackage())
	  	    .addPackage(org.collectalot.collectorapp.model.TitlePart.class.getPackage())
		    .addPackage(org.collectalot.collectorapp.rest.TitlePartRest.class.getPackage())
		    .addPackage(org.collectalot.collectorapp.security.UserSessionResolver.class.getPackage())
		    .addClass(TestImplUserSessionResolver.class)
    	    .addAsResource("META-INF/persistence.xml",
    	      "META-INF/persistence.xml")
    	    .addAsResource("META-INF/load.sql",
    	    	      "META-INF/load.sql")
    	    .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
