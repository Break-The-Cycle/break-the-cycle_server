package brave.btc.domain.record;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import brave.btc.domain.user.UsePerson;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "USE_PERSON_RECORD")
public class Record {

	@Comment("사용개인기록ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_PERSON_RECORD_ID", columnDefinition = "INT NOT NULL AUTO_INCREMENT")
	private Integer id;

	@Comment("기록 구분")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USE_PERSON_ID", columnDefinition = "INT NOT NULL")
	@ToString.Exclude
	private UsePerson usePerson;

	@Comment("기록일시")
	@Column(name = "RECORD_DATE", columnDefinition = "TIMESTAMP NOT NULL")
	private LocalDateTime date;

	@Comment("기록구분")
	@Column(name = "RECORD_DVSN", columnDefinition = "VARCHAR(3) NOT NULL")
	private String division;

	@Comment("기록감정")
	@Column(name = "RECORD_EMOTION", columnDefinition = "VARCHAR(10)")
	private String emotion;

	@OneToMany(mappedBy = "record", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<Picture> pictureList = new ArrayList<>();

	@OneToOne(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
	private Diary diary;

	public void allocatePictureListAndDiary(List<Picture> pictureList, Diary diary) {
		this.pictureList = pictureList;
		this.diary = diary;
	}
}
