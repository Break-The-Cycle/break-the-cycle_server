package brave.btc.domain.app.submission_record;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.app.record.Record;
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
public class SubmissionPicture extends Record {

	@Comment("사진 내용")
	@Column(name = "PICTURE_CONTENT", columnDefinition = "VARCHAR(100) NOT NULL", nullable = false)
	private String content;
}
