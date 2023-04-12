package brave.btc.domain.record;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import brave.btc.domain.user.UsePerson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USE_PERSON_RECORD")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL AUTO_INCREMENT")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL")
	@ToString.Exclude
	private UsePerson usePerson;

	@Column(name = "REPORT_DATE", columnDefinition = "TIMESTAMP NOT NULL")
	private LocalDateTime reportDate;

	@Comment("기록 구분")
	@Column(name = "REPORT_DVSN", columnDefinition = "VARCHAR(3) NOT NULL")
	private String reportDvsn;
}
