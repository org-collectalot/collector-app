package org.collectalot.collectorapp.security;

import javax.servlet.http.HttpServletRequest;

import org.collectalot.collectorapp.model.User;

public interface UserSessionResolver {
	public User getUserLoggedOn(HttpServletRequest req) throws IllegalAccessError;
}
