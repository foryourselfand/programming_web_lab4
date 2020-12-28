package com.foryourselfandRussianHustla.api.v1.auth;

import com.foryourselfandRussianHustla.users.UserEntity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@RequestScoped
public class AuthenticatedUserInjectionProducer {
	private AuthenticationEvent authenticationEvent;
	
	public void handleAuthenticationEvent(@Observes @UserAuthenticated AuthenticationEvent event) {
		this.authenticationEvent = event;
	}
	
	@Produces
	@UserAuthenticated
	public UserEntity getAuthenticatedUser() {
		return authenticationEvent == null ? null : authenticationEvent.userEntity;
	}
}
