package org.collectalot.collectorapp.rest;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.collectalot.collectorapp.db.TitlePartDBBackend;
import org.collectalot.collectorapp.model.TitlePart;
@Path("/title-part")
public class TitlePartRest {
	@Inject
	TitlePartDBBackend tpBackend;

	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TitlePart getTitlePart (@PathParam("id") Long id)
    {
		return tpBackend.getTitlePart(id);
    }

	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public TitlePart[] getTitlePartByParent (@QueryParam("parent") Long parentId)
    {
		if(parentId == null) {
			return tpBackend.getAllTitleParts();
		} else {
			return tpBackend.getAllTitleParts(parentId);
		}
    }
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart saveTitlePart(TitlePart tp) {
		return tpBackend.saveTitlePart(tp);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart addTitlePart(TitlePart tp) {
		return tpBackend.addTitlePart(tp);
	}
	@DELETE
    @Path("/{id}")
    public void delete (@PathParam("id") Long id)
    {
		tpBackend.deleteTitlePart(id);
    }
}
