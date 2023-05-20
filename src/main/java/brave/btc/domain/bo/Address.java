package brave.btc.domain.bo;

import org.hibernate.annotations.Comment;

import brave.btc.constant.enums.AddressDivision;
import brave.btc.util.converter.AddressDivisionToCodeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ADDRESS")
public class Address {


	@Comment("지역 ID")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID", columnDefinition = "INT NOT NULL")
	private Integer id;

	@Convert(converter= AddressDivisionToCodeConverter.class)
	@Comment("지역 구분")
	@Column(name = "ADDRESS_DVSN", columnDefinition = "VARCHAR(3) NOT NULL", nullable = false)
	private AddressDivision division;

	@Comment("우편번호")
	@Column(name = "POSTAL_NUMBER", columnDefinition = "VARCHAR(5) NULL", nullable = true)
	private String postalNumber;

	@Comment("시도")
	@Column(name = "SIDO", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String sido;

	@Comment("시군구")
	@Column(name = "SIGUNGU", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String sigungu;

	@Comment("읍면동")
	@Column(name = "EUPMYEONDONG", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String eupmyeondong;

	@Comment("리")
	@Column(name = "LI", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String li;

	@Comment("도서")
	@Column(name = "ISLAND", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String island;

	@Comment("번지")
	@Column(name = "BUNGEE", columnDefinition = "VARCHAR(10) NULL", nullable = true)
	private String bungee;


	@Comment("상세지역")
	@Column(name = "DETAIL_ADDRESS", columnDefinition = "VARCHAR(100) NULL")
	private String detail;

}
