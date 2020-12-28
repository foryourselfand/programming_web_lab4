package com.foryourselfandRussianHustla.area;

import javax.ejb.Stateless;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Stateless
public class AreaServiceBean implements AreaService {
	private static final BigDecimal MINUS_ONE = BigDecimal.valueOf(- 1);
	
	@Override
	public boolean checkPoint(
			@DecimalMin(value = "-2") @DecimalMax(value = "2") BigDecimal x,
			@DecimalMin(value = "-3", inclusive = false) @DecimalMax(value = "3", inclusive = false) BigDecimal y,
			@DecimalMin(value = "-2") @DecimalMax(value = "2") BigDecimal r
	) {
		if (r.compareTo(BigDecimal.ZERO) == 0)
			return false;
		else if (r.compareTo(BigDecimal.ZERO) < 0)
			return doCheckPointMinus(
					x.multiply(MINUS_ONE).doubleValue(),
					y.multiply(MINUS_ONE).doubleValue(),
					r.multiply(MINUS_ONE).doubleValue()
			);
		return doCheckPoint(x.doubleValue(), y.doubleValue(), r.doubleValue());
	}
	
	private boolean doCheckPoint(double x, double y, double r) {
		double rHalf = r / 2;
		
		boolean firstHalf = (x >= 0 && y >= 0);
		boolean inCircle = (x + y) <= rHalf * rHalf;
		
		boolean thirdHalf = (x <= 0 && y <= 0);
		boolean inSquare = (x >= - r && y >= - r);
		
		boolean forthHalf = (x >= 0 && y <= 0);
		boolean inTriangle = (x <= rHalf && y >= - rHalf && (x - y <= rHalf));
		
		return (
				(firstHalf && inCircle)
						|| (thirdHalf && inSquare)
						|| (forthHalf && inTriangle)
		
		);
	}
	
	private boolean doCheckPointMinus(double x, double y, double r) {
		double rHalf = r / 2;
		
		boolean firstHalf = (x >= 0 && y >= 0);
		boolean inSquare = (x <= r && y <= r);
		
		boolean secondHalf = (x <= 0 && y >= 0);
		boolean inTriangle = (x >= - rHalf && y <= rHalf && (y - x <= rHalf));
		
		boolean thirdHalf = (x <= 0 && y <= 0);
		boolean inCircle = (- x - y) <= rHalf * rHalf;
		
		return (
				(firstHalf && inSquare)
						|| (secondHalf && inTriangle)
						|| (thirdHalf && inCircle)
		
		);
	}
}
