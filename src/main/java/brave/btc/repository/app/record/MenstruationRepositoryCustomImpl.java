package brave.btc.repository.app.record;

import static brave.btc.domain.app.record.QMenstruation.*;
import static brave.btc.repository.app.AppWhereCond.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import brave.btc.domain.app.record.Menstruation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MenstruationRepositoryCustomImpl implements MenstruationRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Menstruation> searchMenstruationList(Integer usePersonId, LocalDate fromDate, LocalDate toDate) {
		return queryFactory.selectFrom(menstruation)
			.where(
				eqUsePerson(usePersonId),
				btwStartDate(fromDate, toDate),
				btwEndDate(fromDate, toDate))
			.orderBy(menstruation.startDate.desc())
			.fetch();
	}

	@Override
	public Menstruation searchLatestMenstruation(Integer usePersonId) {
		return queryFactory.selectFrom(menstruation)
			.where(
				eqUsePerson(usePersonId))
			.orderBy(menstruation.startDate.desc())
			.limit(1)
			.fetchOne();
	}
}
