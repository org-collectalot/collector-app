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
import org.collectalot.collectorapp.security.PreferredUserSessionResolver;
import org.collectalot.collectorapp.security.UserSessionResolver;

@Path("/title-part")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TitlePartRest {
	@Inject
	TitlePartDBBackend tpBackend;
	
	@Inject @PreferredUserSessionResolver
	UserSessionResolver userSessionResolver;
	
	public TitlePartRest() {
		
	}

	public TitlePartRest(TitlePartDBBackend tpBackend, UserSessionResolver userSessionResolver) {
		this.tpBackend = tpBackend;
		this.userSessionResolver = userSessionResolver;
	}
	

	@GET
    @Path("/{id}")
	public TitlePart getTitlePart (@Context HttpServletRequest httpServletRequest, 
	                               @PathParam("id") Long id)
    {
		return tpBackend.getTitlePart(userSessionResolver.getUserLoggedOn(httpServletRequest), id);
    }

	@GET
    @Path("/")
	public TitlePart[] getTitlePartByParent (@Context HttpServletRequest httpServletRequest, 
	                                         @QueryParam("parent") Long parentId)
    {
		TitlePart[] tps = null;
		if(parentId == null) {
			tps = tpBackend.getAllTitleParts(userSessionResolver.getUserLoggedOn(httpServletRequest));
		} else {
			tps = tpBackend.getAllTitleParts(userSessionResolver.getUserLoggedOn(httpServletRequest), parentId);
		}
		if (tps == null) {
			tps = new TitlePart[]{};
		}
		return tps;
    }
	
	@PUT
	@Path("/{id}")
	public TitlePart saveTitlePart(@Context HttpServletRequest httpServletRequest,
	                               @Context HttpServletResponse httpServletResponse,
	                               @PathParam("id") Long id,
	                               TitlePart tp) {
		tp.setId(id);
		try {
			return tpBackend.saveTitlePart(userSessionResolver.getUserLoggedOn(httpServletRequest), tp);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public TitlePart addTitlePart(@Context HttpServletRequest httpServletRequest,
			                      TitlePart tp) {
		return tpBackend.addTitlePart(userSessionResolver.getUserLoggedOn(httpServletRequest), tp);
	}
	
	@DELETE
    @Path("/{id}")
    public void delete (@Context HttpServletRequest httpServletRequest,
                        @PathParam("id") Long id)
    {
		tpBackend.deleteTitlePart(userSessionResolver.getUserLoggedOn(httpServletRequest),id);
    }
}
