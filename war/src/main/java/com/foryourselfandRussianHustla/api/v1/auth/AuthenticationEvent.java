package com.foryourselfandRussianHustla.api.v1.auth;

import com.foryourselfandRussianHustla.users.UserEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationEvent {
	public UserEntity userEntity;
}
