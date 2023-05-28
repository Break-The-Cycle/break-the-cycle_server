package brave.btc.domain.bo.submissionrecord;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.RecordDivision;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(RecordDivision.Values.RECORDING)
@Table(name = "SUBMISSION_RECORDING")
public class SubmissionRecording extends UsePersonSubmissionRecord {

	@Comment("녹음 내용")
	@Column(name = "RECORDING_CONTENT", columnDefinition = "VARCHAR(500) NOT NULL", nullable = false)
	private String content;

	@Comment("녹음 변환 내용")
	@Column(name = "RCRDN_CNVRS_CONTENT", columnDefinition = "VARCHAR(500) NULL", nullable = true)
	private String conversionContent;


}
