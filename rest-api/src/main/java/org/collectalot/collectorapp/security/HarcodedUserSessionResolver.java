package org.collectalot.collectorapp.security;

import javax.servlet.http.HttpServletRequest;

import org.collectalot.collectorapp.model.User;

public class HarcodedUserSessionResolver implements UserSessionResolver {

	@Override
	public User getUserLoggedOn(HttpServletRequest req) {
		User u = new User();
		u.setId(1L);
		u.setName("Jacob Borella");
		return u;
	}

}
