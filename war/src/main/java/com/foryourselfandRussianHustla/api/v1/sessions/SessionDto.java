package com.foryourselfandRussianHustla.api.v1.sessions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SessionDto {
	public final long userId;
	public final String token;
}
