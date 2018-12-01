package org.collectalot.collectorapp.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.collectalot.collectorapp.db.TitlePartDBBackend;
import org.collectalot.collectorapp.model.TitlePart;
import org.collectalot.collectorapp.model.User;
import org.collectalot.collectorapp.security.RestServiceAccessFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TitlePartRestTest {
    private TitlePartDBBackend dbMock;
    private TitlePartRest tpRest;
    private HttpServletRequest httpReqMockWithUser;
    private HttpServletRequest httpReqMockWithNoUser;
    
	@BeforeEach
	void beforeEach() {
		User user = new User();
		user.setId(1L);
		user.setName("Jacob Borella");

		dbMock = mock(TitlePartDBBackend.class);
		TitlePart tp1 = new TitlePart();
		tp1.setId(1L);
		tp1.setText("foobar");
		when(dbMock.getTitlePart(user, 1L)).thenReturn(tp1);
		when(dbMock.getTitlePart(user ,10L)).thenReturn(null);
		tpRest = new TitlePartRest(dbMock);
		
		httpReqMockWithUser = mock(HttpServletRequest.class);
		when(httpReqMockWithUser.getAttribute(RestServiceAccessFilter.USER_LOGGED_ON)).thenReturn(user);
	}
	@Test
	void testGetById() {
		//get a titlepart successfully
		TitlePart tp = tpRest.getTitlePart(httpReqMockWithUser,1L);
		assertSame(1L, tp.getId());
		assertSame("foobar", tp.getText());
		
		//request a non existent titlepart
		assertSame(null, tpRest.getTitlePart(httpReqMockWithUser, 10L));
		
		//request a titlepart with non existent user
		try {
			tpRest.getTitlePart(httpReqMockWithNoUser, 1L);
			assertTrue(false, "this condition shouldn't be reached");
		} catch(IllegalAccessError e) {
			assertTrue(true, "exception thrown as expected");
		}
	}

}
