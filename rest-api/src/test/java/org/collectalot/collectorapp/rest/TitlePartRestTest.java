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
    private HttpServletRequest httpReqMockWithUser0;
	private HttpServletRequest httpReqMockWithNoUser;
	private HttpServletRequest httpReqMockWithInvalidUser;//TODO also test for invalid users
	
	//defined objects to test for
	TitlePart tp0 = new TitlePart();
	TitlePart tp1 = new TitlePart();
	TitlePart tp2 = new TitlePart();
	TitlePart[] tps0 = new TitlePart[]{tp0, tp1};
	TitlePart[] tps1 = new TitlePart[]{tp2};

	@BeforeEach
	void beforeEach() {
		User user = new User();
		user.setId(1L);
		user.setName("Jacob Borella");

		dbMock = mock(TitlePartDBBackend.class);
		tp0.setId(1L);
		tp0.setText("foo");
		tp1.setId(2L);
		tp1.setText("bar");
		tp2.setId(2L);
		tp2.setText("pip");

		when(dbMock.getTitlePart(user, 1L)).thenReturn(tp0);
		when(dbMock.getTitlePart(user ,10L)).thenReturn(null);
		when(dbMock.getAllTitleParts(user)).thenReturn(tps0);
		when(dbMock.getAllTitleParts(user, 1L)).thenReturn(new TitlePart[]{});
		when(dbMock.getAllTitleParts(user, 2L)).thenReturn(tps1);
		tpRest = new TitlePartRest(dbMock);
		
		httpReqMockWithUser0 = mock(HttpServletRequest.class);
		when(httpReqMockWithUser0.getAttribute(RestServiceAccessFilter.USER_LOGGED_ON)).thenReturn(user);
	}
	@Test
	void testGetByIdUser0() {
		//get a titlepart successfully
		TitlePart tp = tpRest.getTitlePart(httpReqMockWithUser0,1L);
		assertSame(1L, tp.getId());
		assertSame("foo", tp.getText());
		
		//request a non existent titlepart
		assertSame(null, tpRest.getTitlePart(httpReqMockWithUser0, 10L));
		
		//request a titlepart with non existent user
		try {
			tpRest.getTitlePart(httpReqMockWithNoUser, 1L);
			assertTrue(false, "this condition shouldn't be reached");
		} catch(IllegalAccessError e) {
			assertTrue(true, "exception thrown as expected");
		}
	}
	@Test
	void testGetTitlePartByParentUser0() {
		//get a list of titleparts with no parent successfully
		TitlePart[] tps = tpRest.getTitlePartByParent(httpReqMockWithUser0, null);
		assertArrayEquals(tps0, tps);

		//get an empty list of titleparts successfully
		tps = tpRest.getTitlePartByParent(httpReqMockWithUser0, 1L);
		assertSame(0, tps.length);

		//get a list of titleparts by using id
		tps = tpRest.getTitlePartByParent(httpReqMockWithUser0, 2L);
		assertArrayEquals(tps1, tps);

		//request a titlepart with parent with non existent user
		try {
			tpRest.getTitlePartByParent(httpReqMockWithNoUser, 1L);
			assertTrue(false, "this condition shouldn't be reached");
		} catch(IllegalAccessError e) {
			assertTrue(true, "exception thrown as expected");
		}
		try {
			tpRest.getTitlePartByParent(httpReqMockWithNoUser, null);
			assertTrue(false, "this condition shouldn't be reached");
		} catch(IllegalAccessError e) {
			assertTrue(true, "exception thrown as expected");
		}
	}
}
