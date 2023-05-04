package brave.btc.repository.app;

import static brave.btc.domain.app.record.QMenstruation.*;
import static brave.btc.domain.app.user.QUsePerson.*;

import java.time.LocalDate;

import com.querydsl.core.types.dsl.BooleanExpression;

public abstract class AppWhereCond {

	public static BooleanExpression eqUsePerson(Integer usePersonId) {
		return usePersonId!=null ? usePerson.id.eq(usePersonId) : null;
	}

	public static BooleanExpression btwStartDate(LocalDate fromDate, LocalDate toDate) {
		return fromDate!=null && toDate!=null ? menstruation.startDate.between(fromDate,toDate) : null;
	}

	public static BooleanExpression btwEndDate(LocalDate fromDate, LocalDate toDate) {
		return fromDate!=null && toDate!=null ? menstruation.endDate.between(fromDate,toDate) : null;
	}

}
