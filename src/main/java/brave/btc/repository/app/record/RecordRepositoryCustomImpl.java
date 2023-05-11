package brave.btc.repository.app.record;

import static brave.btc.domain.app.record.QRecord.*;
import static brave.btc.repository.app.AppWhereCond.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import brave.btc.domain.app.record.Record;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	private final Environment environment;
	@Override
	public List<String> searchViolentRecordDateList(int usePersonId, LocalDate fromDate, LocalDate toDate) {

		String[] activeProfiles = environment.getActiveProfiles();
		String activeProfile = activeProfiles[0];
		StringTemplate formattedDate;
		if (activeProfile.equals("dev") || activeProfile.equals("master")) {
			 formattedDate = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})"
			, record.datetime
			, ConstantImpl.create("%Y-%m-%d"));
		} else {
			formattedDate = Expressions.stringTemplate(
			"FORMATDATETIME({0}, {1})"
			, record.datetime
			, ConstantImpl.create("yyyy-MM-dd"));
		}

		return queryFactory
			.select(formattedDate)
			.from(record)
			.where(
				eqUsePerson(usePersonId),
				btwDateTime(fromDate, toDate)
			)
			.groupBy(formattedDate)
			.orderBy(record.datetime.asc())
			.fetch();
	}

	@Override
	public List<Record> searchViolentRecordList(int usePersonId, LocalDate targetDate) {
		return queryFactory.selectFrom(record)
			.where(
				eqUsePerson(usePersonId),
				eqDate(targetDate)
			)
			.orderBy(record.datetime.asc())
			.fetch();
	}
}
