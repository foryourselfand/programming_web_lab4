package com.foryourselfandRussianHustla.history;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.foryourselfandRussianHustla.users.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "history")
@NamedQuery(name = "history.findByUser", query = "from history where user = :user order by id asc")
@NamedQuery(name = "history.findByUserDesc", query = "from history where user = :user order by id desc")
@NamedQuery(name = "history.deleteByUser", query = "delete from history where user = :user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class HistoryEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	@Column(nullable = false, precision = 25, scale = 20)
	private BigDecimal x, y, r;
	
	private boolean result;
}
