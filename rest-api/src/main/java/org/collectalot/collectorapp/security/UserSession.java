package org.collectalot.collectorapp.security;

import org.collectalot.collectorapp.model.User;

public class UserSession {
	public static User getUserLoggedOn(){
		User user = new User();//TODO get from session
		user.setId(1L);
		user.setName("jb");
		return user;
	}
}
