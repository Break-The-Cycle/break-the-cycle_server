package brave.btc.domain.record;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "RECORDING")
public class Recording {

	@Comment("개인 기록 ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("개인 기록")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_RECORD_ID")
	@MapsId
	@ToString.Exclude
	private Record record;

	@Comment("녹음내용")
	@Column(name = "RECORDING_CONTENT", columnDefinition = "VARCHAR(100) NOT NULL")
	private String content;

	@Comment("녹음변환내용")
	@Column(name = "RCRDN_CNVRS_CONTENT", columnDefinition = "VARCHAR(100) NULL")
	private String conversionContent;
}
