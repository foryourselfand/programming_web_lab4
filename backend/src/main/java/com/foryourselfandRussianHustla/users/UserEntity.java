package com.foryourselfandRussianHustla.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "users")
@NamedQuery(name = "users.findByUsername", query = "from users where username = :username")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, name = "password_hash")
	private String passwordHash;
	
	@Column(nullable = false, name = "password_salt")
	private String passwordSalt;
}
