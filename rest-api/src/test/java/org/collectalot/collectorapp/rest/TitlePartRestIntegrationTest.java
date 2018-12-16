package org.collectalot.collectorapp.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.collectalot.collectorapp.model.TitlePart;
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
    @Test
    @RunAsClient
    public void testGetTitlePartNoId() {
        Client client = ClientBuilder.newClient();
        //test finding all items with no parent specified (root elements)
        WebTarget target = client.target("http://localhost:8080")
                .path("rest").path("title-part");

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
        String expected =
            "[{\"id\":2,\"parentId\":null,\"text\":\"Lucky Luke\",\"version\":1,\"deleted\":false}," +
            "{\"id\":3,\"parentId\":null,\"text\":\"Asterix\",\"version\":1,\"deleted\":false}," +
            "{\"id\":4,\"parentId\":null,\"text\":\"Anders And & Co\",\"version\":1,\"deleted\":false}," +
            "{\"id\":5,\"parentId\":null,\"text\":\"Walt Disney's Comics & Stories\",\"version\":1,\"deleted\":false}," +
            "{\"id\":6,\"parentId\":null,\"text\":\"Fart og Tempo\",\"version\":1,\"deleted\":false}," +
            "{\"id\":7,\"parentId\":null,\"text\":\"Vakse Viggo\",\"version\":1,\"deleted\":false}," +
            "{\"id\":8,\"parentId\":null,\"text\":\"Iznogood\",\"version\":1,\"deleted\":false}]";
        Assert.assertEquals(expected, response.readEntity(String.class));
    }

    @Test
    @RunAsClient
    public void testPutExistingTitlePart() {
        Client client = ClientBuilder.newClient();
        //test updating an item, which exists, but with wrong version number
        WebTarget target = client.target("http://localhost:8080")
                .path("rest").path("title-part").path("2");
        
        TitlePart tpUpdated = new TitlePart();
        tpUpdated.setId(2L);
        tpUpdated.setParentId(null);
        tpUpdated.setText("FooBar");
        tpUpdated.setVersion(1);
        tpUpdated.setDeleted(false);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response =
            invocationBuilder.put(Entity.entity(tpUpdated, MediaType.APPLICATION_JSON));
        tpUpdated = response.readEntity(TitlePart.class);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(2, tpUpdated.getVersion());
    }

    @Test
    @RunAsClient
    public void testPutExistingTitlePartWithWrongVersion() {
        Client client = ClientBuilder.newClient();
        //test updating an item, which exists
        WebTarget target = client.target("http://localhost:8080")
                .path("rest").path("title-part").path("2");
        
        TitlePart tpUpdated = new TitlePart();
        tpUpdated.setId(3L);
        tpUpdated.setParentId(null);
        tpUpdated.setText("Tintin");
        tpUpdated.setVersion(2);
        tpUpdated.setDeleted(false);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response =
            invocationBuilder.put(Entity.entity(tpUpdated, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response.getStatus());
    }

    //do a put on a deleted titlepart
    //do a put with a non existing id
    //do a put with no id at all

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
