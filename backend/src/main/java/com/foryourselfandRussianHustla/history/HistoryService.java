package com.foryourselfandRussianHustla.history;

import com.foryourselfandRussianHustla.users.UserEntity;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.Deque;

@Remote
public interface HistoryService extends Serializable {
	Deque<HistoryEntity> getQueries(UserEntity user);
	
	Deque<HistoryEntity> getQueries(UserEntity user, long offset, long count);
	
	boolean addQuery(HistoryEntity query);
	
	boolean clear(UserEntity user);
}
