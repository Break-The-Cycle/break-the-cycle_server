package brave.btc.domain.record;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "DIARY")
public class Diary {

	@Comment("개인 기록 ID")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("개인 기록")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_RECORD_ID")
	@MapsId
	@ToString.Exclude
	private Record record;

	@Comment("일기 제목")
	@Column(name = "DIARY_TITLE", columnDefinition = "VARCHAR(100) NOT NULL")
	private String title;

	@Comment("일기 내용")
	@Column(name = "DIARY_CONTENT", columnDefinition = "VARCHAR(100) NOT NULL")
	private String content;
}
