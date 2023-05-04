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
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.menstruation.MenstruationDto;
import brave.btc.exception.menstruation.InvalidMenstruationInfoException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.app.record.RecordRepository;

@ExtendWith(MockitoExtension.class)
class UsePersonAndMenstruationServiceImplTest {

	@Mock
	private UsePersonRepository usePersonRepository;

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
		UsePerson usePerson = UsePerson.builder()
			.id(usePersonId).build();

		when(usePersonRepository.findById(eq(usePersonId)))
			.thenReturn(Optional.of(usePerson));

		// when
		CommonResponseDto<?> result = menstruationService.createMenstruationInfo(usePersonId, createDto);

		// then
		verify(usePersonRepository, times(1)).findById(eq(usePersonId));
		verify(recordRepository, times(1)).save(any(Menstruation.class));
		// check result
		assertThat(result.getMessage()).isEqualTo("생리 기록이 정상적으로 등록되었습니다.");
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
		assertThrows(InvalidMenstruationInfoException.class, () -> {
			menstruationService.createMenstruationInfo(usePersonId, createDto);
		});
		verifyNoMoreInteractions(recordRepository);
	}

}