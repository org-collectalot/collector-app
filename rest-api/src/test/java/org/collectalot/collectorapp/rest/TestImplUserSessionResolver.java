package org.collectalot.collectorapp.rest;

import javax.enterprise.inject.Alternative;
import javax.servlet.http.HttpServletRequest;

import org.collectalot.collectorapp.model.User;
import org.collectalot.collectorapp.security.UserSessionResolver;
//https://docs.jboss.org/weld/reference/latest/en-US/html/injection.html
@Alternative
public class TestImplUserSessionResolver implements UserSessionResolver {
	@Override
	public User getUserLoggedOn(HttpServletRequest req) {
		User mockUser = new User();
		mockUser.setId(1L);
		mockUser.setName("jb");
		return mockUser;
	}
}
