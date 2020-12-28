package com.foryourselfandRussianHustla.api.v1.sessions;

import lombok.NoArgsConstructor;
import com.foryourselfandRussianHustla.api.v1.auth.Secured;
import com.foryourselfandRussianHustla.api.v1.auth.UserAuthenticated;
import com.foryourselfandRussianHustla.sessions.SessionsService;
import com.foryourselfandRussianHustla.users.UserEntity;
import com.foryourselfandRussianHustla.users.UsersService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class SessionsController {
	private SessionsService sessionsService;
	private UsersService usersService;
	private UserEntity userEntityAuthenticated;
	
	@Inject
	public SessionsController(
			SessionsService sessionsService,
			UsersService usersService,
			@UserAuthenticated UserEntity userEntityAuthenticated
	) {
		this.sessionsService = sessionsService;
		this.usersService = usersService;
		this.userEntityAuthenticated = userEntityAuthenticated;
	}
	
	@GET
	@Secured
	@Path("/list")
	public List<String> list() {
		return sessionsService.getSessionTokens(Objects.requireNonNull(userEntityAuthenticated));
	}
	
	@POST
	@Path("/create")
	public Response createFromForm(
			@QueryParam("username") String username,
			@QueryParam("password") String password
	) {
		UserEntity user = usersService.findUser(username);
		
		if (user != null && usersService.checkPassword(user, password)) {
			String token = sessionsService.createSession(user);
			return Response.ok(new SessionDto(user.getId(), token)).build();
		}
		
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Secured
	@Path("/check")
	public boolean check(@QueryParam("token") String token) {
		return sessionsService.checkSession(userEntityAuthenticated, token);
	}
	
	@POST
	@Secured
	@Path("/destroy")
	public boolean destroy(@QueryParam("token") String token) {
		return sessionsService.destroySession(userEntityAuthenticated, token);
	}
}
