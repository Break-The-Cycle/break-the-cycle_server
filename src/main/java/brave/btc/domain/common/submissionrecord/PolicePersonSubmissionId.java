package brave.btc.domain.common.submissionrecord;

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
public class PolicePersonSubmissionId implements Serializable {
	private Integer managePersonId;
	private Integer submissionRecordId;
}

