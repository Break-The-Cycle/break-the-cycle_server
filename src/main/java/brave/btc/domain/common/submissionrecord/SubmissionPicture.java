package brave.btc.domain.common.submissionrecord;

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
@DiscriminatorValue(RecordDivision.Values.PICTURE)
@Table(name = "SUBMISSION_PICTURE")
public class SubmissionPicture extends UsePersonSubmissionRecord {

	@Comment("사진 내용")
	@Column(name = "PICTURE_CONTENT", columnDefinition = "VARCHAR(500) NOT NULL", nullable = false)
	private String content;
}
