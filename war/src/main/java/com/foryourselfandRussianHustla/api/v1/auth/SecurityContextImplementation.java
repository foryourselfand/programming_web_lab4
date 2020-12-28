package com.foryourselfandRussianHustla.api.v1.auth;

import lombok.AllArgsConstructor;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@AllArgsConstructor
class SecurityContextImplementation implements SecurityContext {
	private static final String AUTHENTICATION_SCHEME = "";
	
	private final SecurityContext securityContext;
	private final String username;
	
	@Override
	public Principal getUserPrincipal() {
		return ()->username;
	}
	
	@Override
	public boolean isUserInRole(String role) {
		return "USER".equals(role);
	}
	
	@Override
	public boolean isSecure() {
		return securityContext.isSecure();
	}
	
	@Override
	public String getAuthenticationScheme() {
		return AUTHENTICATION_SCHEME;
	}
}
