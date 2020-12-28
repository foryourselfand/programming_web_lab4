package com.foryourselfandRussianHustla.sessions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.foryourselfandRussianHustla.users.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "sessions")
@IdClass(SessionEntity.PrimaryKey.class)
@NamedQuery(name = "sessions.findByUser", query = "from sessions where user = :user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SessionEntity {
	@Id
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	@Id
	@Column(nullable = false)
	private String token;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@EqualsAndHashCode
	public static class PrimaryKey implements Serializable {
		private UserEntity user;
		private String token;
	}
}
