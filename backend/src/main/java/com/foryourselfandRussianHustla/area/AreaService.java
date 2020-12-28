package com.foryourselfandRussianHustla.area;

import javax.ejb.Remote;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Remote
public interface AreaService extends Serializable {
	boolean checkPoint(
			@DecimalMin(value = "-2") @DecimalMax(value = "2") BigDecimal x,
			@DecimalMin(value = "-3", inclusive = false) @DecimalMax(value = "3", inclusive = false) BigDecimal y,
			@DecimalMin(value = "-2") @DecimalMax(value = "2") BigDecimal r
	);
}
