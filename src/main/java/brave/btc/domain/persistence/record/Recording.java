package brave.btc.domain.persistence.record;

import org.hibernate.annotations.Comment;

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
@DiscriminatorValue("002")
@Table(name = "RECORDING")
public class Recording extends Record{

	@Comment("녹음내용")
	@Column(name = "RECORDING_CONTENT", columnDefinition = "VARCHAR(100) NOT NULL")
	private String content;

	@Comment("녹음변환내용")
	@Column(name = "RCRDN_CNVRS_CONTENT", columnDefinition = "VARCHAR(100) NULL")
	private String conversionContent;
}
