package brave.btc.domain.bo.submissionrecord;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CounselingPersonSubmissionId implements Serializable {
	private Integer managePersonId;
	private Integer submissionRecordId;


}