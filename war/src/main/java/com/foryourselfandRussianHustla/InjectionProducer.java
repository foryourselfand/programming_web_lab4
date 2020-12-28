package com.foryourselfandRussianHustla;

import com.foryourselfandRussianHustla.area.AreaService;
import com.foryourselfandRussianHustla.history.HistoryService;
import com.foryourselfandRussianHustla.sessions.SessionsService;
import com.foryourselfandRussianHustla.users.UsersService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class InjectionProducer {
	@Produces
	@EJB
	private AreaService areaService;
	
	@Produces
	@EJB
	private HistoryService historyService;
	
	@Produces
	@EJB
	private SessionsService sessionsService;
	
	@Produces
	@EJB
	private UsersService usersService;
}
