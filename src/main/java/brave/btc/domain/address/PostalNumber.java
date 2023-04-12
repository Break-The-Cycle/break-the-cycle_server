package brave.btc.domain.address;

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
@Table(name = "POSTAL_NUMBER")
public class PostalNumber {

	@Comment("지역 ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Comment("주소")
	@MapsId
	@JoinColumn(name = "ADDRESS_ID")
	@OneToOne(fetch= FetchType.LAZY)
	private Address address;

	@Comment("우편번호")
	@Column(name = "POSTAL_NUMBER", columnDefinition = "VARCHAR(5) NULL")
	private String number;

	@Comment("시도")
	@Column(name = "SIDO", columnDefinition = "VARCHAR(10) NULL")
	private String sido;

	@Comment("시군구")
	@Column(name = "SIGUNGU", columnDefinition = "VARCHAR(10) NULL")
	private String sigungu;

	@Comment("읍면동")
	@Column(name = "EUPMYEONDONG", columnDefinition = "VARCHAR(10) NULL")
	private String eupmyeondong;

	@Comment("리")
	@Column(name = "LI", columnDefinition = "VARCHAR(10) NULL")
	private String li;

	@Comment("도서")
	@Column(name = "ISLAND", columnDefinition = "VARCHAR(10) NULL")
	private String island;

	@Comment("번지")
	@Column(name = "BUNGEE", columnDefinition = "VARCHAR(10) NULL")
	private String bungee;

}
