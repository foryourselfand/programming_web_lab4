package com.foryourselfandRussianHustla.api.v1;

import lombok.NoArgsConstructor;
import com.foryourselfandRussianHustla.api.v1.auth.Secured;
import com.foryourselfandRussianHustla.api.v1.auth.UserAuthenticated;
import com.foryourselfandRussianHustla.area.AreaService;
import com.foryourselfandRussianHustla.history.HistoryEntity;
import com.foryourselfandRussianHustla.history.HistoryService;
import com.foryourselfandRussianHustla.users.UserEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Secured
@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class AreaController {
	private AreaService areaService;
	private HistoryService historyService;
	private UserEntity userAuthenticated;
	
	@Inject
	public AreaController(
			AreaService areaService,
			HistoryService historyService,
			@UserAuthenticated UserEntity userAuthenticated
	) {
		this.areaService = areaService;
		this.historyService = historyService;
		this.userAuthenticated = userAuthenticated;
	}
	
	@GET
	@Path("/check/")
	public boolean check(
			@QueryParam("x") BigDecimal x,
			@QueryParam("y") BigDecimal y,
			@QueryParam("r") BigDecimal r
	) {
		return areaService.checkPoint(x, y, r);
	}
	
	@POST
	@Path("/check")
	public boolean checkAndSave(
			@QueryParam("x") BigDecimal x,
			@QueryParam("y") BigDecimal y,
			@QueryParam("r") BigDecimal r
	) {
		boolean result = check(x, y, r);
		historyService.addQuery(new HistoryEntity(null, userAuthenticated, x, y, r, result));
		
		return result;
	}
}
