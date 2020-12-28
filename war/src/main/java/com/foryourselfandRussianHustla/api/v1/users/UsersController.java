package com.foryourselfandRussianHustla.api.v1.users;

import lombok.NoArgsConstructor;
import com.foryourselfandRussianHustla.api.v1.auth.Secured;
import com.foryourselfandRussianHustla.api.v1.auth.UserAuthenticated;
import com.foryourselfandRussianHustla.users.UserEntity;
import com.foryourselfandRussianHustla.users.UsersService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class UsersController {
	private UsersService usersService;
	private UserEntity userEntityAuthenticated;
	
	@Inject
	public UsersController(
			UsersService usersService,
			@UserAuthenticated UserEntity userEntityAuthenticated
	) {
		this.usersService = usersService;
		this.userEntityAuthenticated = userEntityAuthenticated;
	}
	
	@Secured
	@GET
	@Path("/get")
	public UserDto get() {
		return entityToDto(userEntityAuthenticated);
	}
	
	@GET
	@Path("/get/{userId}")
	public Response get(@PathParam("userId") long userId) {
		UserEntity user = usersService.getUser(userId);
		
		if (user != null)
			return Response.ok(entityToDto(user)).build();
		return Response.status(Response.Status.NOT_FOUND).entity("User not found for id " + userId).build();
	}
	
	@GET
	@Path("/find/{username}")
	public Response find(@PathParam("username") String username) {
		UserEntity user = usersService.findUser(username);
		
		if (user != null)
			return Response.ok(entityToDto(user)).build();
		return Response.status(Response.Status.NOT_FOUND).entity("User not found for name " + username).build();
	}
	
	@POST
	@Path("/create")
	public Response create(
			@QueryParam("username") String username,
			@QueryParam("password") String password
	) {
		UserEntity user = usersService.createUser(username, password);
		
		if (user != null)
			return Response.ok(user.getId()).build();
		return Response.status(Response.Status.BAD_REQUEST).entity("User with name " + username + " already exist").build();
	}
	
	@Secured
	@DELETE
	@Path("/remove")
	public boolean remove() {
		return usersService.removeUser(userEntityAuthenticated);
	}
	
	private UserDto entityToDto(UserEntity entity) {
		return new UserDto(entity.getId(), entity.getUsername());
	}
}
