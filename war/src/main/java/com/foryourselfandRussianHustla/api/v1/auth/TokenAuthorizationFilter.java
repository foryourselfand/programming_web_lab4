package com.foryourselfandRussianHustla.api.v1.auth;

import com.foryourselfandRussianHustla.sessions.SessionsService;
import com.foryourselfandRussianHustla.users.UserEntity;
import com.foryourselfandRussianHustla.users.UsersService;
import lombok.NoArgsConstructor;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
@NoArgsConstructor
public class TokenAuthorizationFilter implements ContainerRequestFilter {
	private static final String USER_ID_HEADER = "X-USER-ID";
	private static final String TOKEN_HEADER = "X-TOKEN";
	
	private SessionsService sessionsService;
	private UsersService usersService;
	private Event<AuthenticationEvent> userAuthenticatedEvent;
	
	@Inject
	public TokenAuthorizationFilter(
			SessionsService sessionsService,
			UsersService usersService,
			@UserAuthenticated Event<AuthenticationEvent> userAuthenticatedEvent
	) {
		this.sessionsService = sessionsService;
		this.usersService = usersService;
		this.userAuthenticatedEvent = userAuthenticatedEvent;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) {
		if (! authorize(requestContext))
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	}
	
	private boolean authorize(ContainerRequestContext requestContext) {
		final String userIdString = requestContext.getHeaderString(USER_ID_HEADER);
		final String token = requestContext.getHeaderString(TOKEN_HEADER);
		
		if (userIdString == null || token == null)
			return false;
		
		long userId;
		try {
			userId = Long.parseLong(userIdString);
		} catch (NumberFormatException e) {
			return false;
		}
		
		UserEntity user = usersService.getUser(userId);
		if (user == null)
			return false;
		
		if (! sessionsService.checkSession(user, token))
			return false;
		
		requestContext.setSecurityContext(new SecurityContextImplementation(requestContext.getSecurityContext(), user.getUsername()));
		
		userAuthenticatedEvent.fire(new AuthenticationEvent(user));
		
		return true;
	}
}
