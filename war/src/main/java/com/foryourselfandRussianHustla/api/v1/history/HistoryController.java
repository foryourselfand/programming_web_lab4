package com.foryourselfandRussianHustla.api.v1.history;

import com.foryourselfandRussianHustla.api.v1.auth.Secured;
import com.foryourselfandRussianHustla.api.v1.auth.UserAuthenticated;
import com.foryourselfandRussianHustla.history.HistoryEntity;
import com.foryourselfandRussianHustla.history.HistoryService;
import com.foryourselfandRussianHustla.users.UserEntity;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Secured
@Path("/history")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class HistoryController {
	private HistoryService historyService;
	private UserEntity userEntityAuthenticated;
	
	@Inject
	public HistoryController(
			HistoryService historyService,
			@UserAuthenticated UserEntity userEntityAuthenticated
	) {
		this.historyService = historyService;
		this.userEntityAuthenticated = userEntityAuthenticated;
	}
	
	@GET
	@Path("/get")
	public List<HistoryDto> get() {
		return historyService.getQueries(userEntityAuthenticated).stream()
				.map(this::entityToDto).collect(Collectors.toList());
	}
	
	@GET
	@Path("/get/page/offset/{offset}/count/{count}")
	public List<HistoryDto> getPage(
			@PathParam("offset") long offset,
			@PathParam("count") long count
	) {
		return historyService.getQueries(userEntityAuthenticated, offset, count).
				stream().map(this::entityToDto).collect(Collectors.toList());
	}
	
	@POST
	@Path("/clear")
	public boolean clear() {
		return historyService.clear(userEntityAuthenticated);
	}
	
	private HistoryDto entityToDto(HistoryEntity historyEntity) {
		return new HistoryDto(
				historyEntity.getX().toPlainString(),
				historyEntity.getY().toPlainString(),
				historyEntity.getR().toPlainString(),
				historyEntity.isResult()
		);
	}
}
