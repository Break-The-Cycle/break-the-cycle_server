package brave.btc.repository.app.record;

import static brave.btc.domain.app.record.QRecord.*;
import static brave.btc.repository.app.AppWhereCond.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.app.record.Record;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public List<LocalDate> searchViolentRecordDateList(int usePersonId, LocalDate fromDate, LocalDate toDate) {

		List<RecordDivision> violentRecordDivisionList = Arrays.asList(RecordDivision.PICTURE, RecordDivision.DIARY, RecordDivision.RECORDING);
		return queryFactory
			.select(record.reportDate)
			.from(record)
			.where(
				eqUsePerson(usePersonId),
				btwReportDate(fromDate, toDate),
				record.recordDivision.in(violentRecordDivisionList)
			)
			.groupBy(record.reportDate)
			.orderBy(record.reportDate.asc())
			.fetch();
	}

	@Override
	public List<Record> searchViolentRecordList(int usePersonId, LocalDate targetDate) {

		List<RecordDivision> violentRecordDivisionList = Arrays.asList(RecordDivision.PICTURE, RecordDivision.DIARY, RecordDivision.RECORDING);
		return queryFactory.selectFrom(record)
			.where(
				eqUsePerson(usePersonId),
				eqDate(targetDate),
				record.recordDivision.in(violentRecordDivisionList)
			)
			.orderBy(record.reportDate.asc())
			.fetch();
	}

	@Override
	public List<Record> searchAllViolentRecordList(int usePersonId, LocalDate fromDate, LocalDate toDate) {
		List<RecordDivision> violentRecordDivisionList = Arrays.asList(RecordDivision.PICTURE, RecordDivision.DIARY, RecordDivision.RECORDING);
		return queryFactory
			.selectFrom(record)
			.where(
				eqUsePerson(usePersonId),
				btwReportDate(fromDate, toDate),
				record.recordDivision.in(violentRecordDivisionList)
			)
			.orderBy(record.reportDate.asc())
			.fetch();
	}
}
