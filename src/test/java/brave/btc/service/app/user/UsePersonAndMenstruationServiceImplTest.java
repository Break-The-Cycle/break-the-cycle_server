package brave.btc.service.app.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import brave.btc.domain.app.record.Menstruation;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.exception.menstruation.InvalidMenstruationException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.app.record.RecordRepository;

@ExtendWith(MockitoExtension.class)
class UsePersonAndMenstruationServiceImplTest {

	@Mock
	private UsePersonRepository usePersonRepository;

	@Mock
	private UsePerson usePerson;

	@Mock
	private RecordRepository recordRepository;


	@InjectMocks
	private UsePersonAndMenstruationServiceImpl menstruationService;

	@Test
	@DisplayName("생리 기록 정상 등록")
	void testCreateMenstruationInfo() {
		// given
		LocalDate startDate = LocalDate.of(2023, 5, 1);
		LocalDate endDate = LocalDate.of(2023, 5, 5);
		MenstruationDto.Create createDto = new MenstruationDto.Create(startDate, endDate);

		int usePersonId = 1;
		when(usePersonRepository.findById(eq(usePersonId))).thenReturn(Optional.of(usePerson));

		// when
		String result = menstruationService.createMenstruationInfo(usePersonId, createDto);

		// then
		verify(usePersonRepository, times(1)).findById(eq(usePersonId));
		verify(recordRepository, times(1)).save(any(Menstruation.class));
		// check result
		assertThat(result).isEqualTo("생리 기록이 정상적으로 등록되었습니다.");
	}

	@Test
	@DisplayName("생리 기록 등록 - 날짜 이상")
	public void testCreateMenstruationInfo_InvalidDate() {
		// given
		LocalDate startDate = LocalDate.of(2023, 5, 5);
		LocalDate endDate = LocalDate.of(2023, 5, 1);
		MenstruationDto.Create createDto = new MenstruationDto.Create(startDate, endDate);

		int usePersonId = 1;

		// when, then
		assertThrows(InvalidMenstruationException.class, () -> {
			menstruationService.createMenstruationInfo(usePersonId, createDto);
		});
		verifyNoMoreInteractions(recordRepository);
	}

	@Test
	@DisplayName("사용자 생리 주기 수정")
	public void testModifyUsePersonMenstruationPeriod() {
		// given
		int usePersonId = 1;
		int menstruationPeriod = 30;
		when(usePersonRepository.findById(eq(usePersonId))).thenReturn(Optional.of(usePerson));

		// when
		String result = menstruationService.modifyUsePersonMenstruationPeriod(usePersonId, menstruationPeriod);

		// then
		verify(usePersonRepository, times(1)).findById(eq(usePersonId));
		verify(usePerson, times(1)).changeMenstruationPeriod(eq(menstruationPeriod));
		verify(usePersonRepository, times(0)).save(eq(usePerson));
		verifyNoMoreInteractions(usePersonRepository, usePerson);
		// check result
		assertThat(result).isEqualTo("유저의 생리 주기가 정상적으로 변경되었습니다.");
	}

	@Test
	@DisplayName("사용자 생리 주기 수정 - 유저 찾을 수 없음")
	public void testModifyUsePersonMenstruationPeriod_UserNotFound() {
		// given
		int usePersonId = 1;
		int menstruationPeriod = 30;
		when(usePersonRepository.findById(eq(usePersonId))).thenReturn(Optional.empty());

		// when, then
		assertThrows(UserPrincipalNotFoundException.class, () -> {
			menstruationService.modifyUsePersonMenstruationPeriod(usePersonId, menstruationPeriod);
		});
		verify(usePersonRepository, times(1)).findById(eq(usePersonId));
		verifyNoMoreInteractions(usePersonRepository);
	}
}