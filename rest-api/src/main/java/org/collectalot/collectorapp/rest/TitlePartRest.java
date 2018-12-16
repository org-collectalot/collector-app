package org.collectalot.collectorapp.rest;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.collectalot.collectorapp.db.TitlePartDBBackend;
import org.collectalot.collectorapp.model.TitlePart;
import org.collectalot.collectorapp.model.User;
import org.collectalot.collectorapp.security.RestServiceAccessFilter;
@Path("/title-part")
public class TitlePartRest {
	@Inject
	TitlePartDBBackend tpBackend;
	
	public TitlePartRest() {
	}
	public TitlePartRest(TitlePartDBBackend tpBackend) {
		this.tpBackend = tpBackend;
	}
	

	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart getTitlePart (@Context HttpServletRequest httpServletRequest, 
	                               @PathParam("id") Long id)
    {
		return tpBackend.getTitlePart(getUser(httpServletRequest), id);
    }

	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart[] getTitlePartByParent (@Context HttpServletRequest httpServletRequest, 
	                                         @QueryParam("parent") Long parentId)
    {
		TitlePart[] tps = null;
		if(parentId == null) {
			tps = tpBackend.getAllTitleParts(getUser(httpServletRequest));
		} else {
			tps = tpBackend.getAllTitleParts(getUser(httpServletRequest), parentId);
		}
		if (tps == null) {
			tps = new TitlePart[]{};
		}
		return tps;
    }
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart saveTitlePart(@Context HttpServletRequest httpServletRequest,
	                               @Context HttpServletResponse httpServletResponse,
	                               @PathParam("id") Long id,
	                               TitlePart tp) {
		tp.setId(id);
		try {
			return tpBackend.saveTitlePart(getUser(httpServletRequest), tp);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			throw new BadRequestException(e.getMessage());
		}
	}
/*
	
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
    }*/
	private User getUser(HttpServletRequest req) {
		try {
			return (User) req.getAttribute(RestServiceAccessFilter.USER_LOGGED_ON);
		} catch(NullPointerException e) {
			throw new IllegalAccessError("Tried to access service with no user logged on.");
		}
	}
}
