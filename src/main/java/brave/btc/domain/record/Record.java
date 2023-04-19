package brave.btc.domain.record;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.user.UsePerson;
import brave.btc.util.converter.RecordDivisionToCodeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="division")
@Table(name = "USE_PERSON_RECORD")
public class Record {

	@Comment("사용개인기록ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("기록 구분")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL", nullable = false)
	@ToString.Exclude
	private UsePerson usePerson;

	@Comment("기록일시")
	@Column(name = "RECORD_DATE", columnDefinition = "TIMESTAMP NOT NULL", nullable = false)
	private LocalDateTime date;

	@Comment("기록구분")
	@Convert(converter = RecordDivisionToCodeConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "RECORD_DVSN", nullable = false)
	private RecordDivision division;

}
