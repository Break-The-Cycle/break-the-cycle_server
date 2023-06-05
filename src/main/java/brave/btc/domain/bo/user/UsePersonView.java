package brave.btc.domain.bo.user;

import org.hibernate.annotations.Comment;

import net.jcip.annotations.Immutable;

import brave.btc.dto.common.auth.UsePersonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Immutable
@Table(name = "USE_PERSON_VIEW")
public class UsePersonView {

	@Comment("사용개인ID")
	@Id
	@Column(name = "USE_PERSON_ID", columnDefinition = "INT NOT NULL")
	private Integer id;


	@Comment("사용개인이름")
	@Column(name = "USE_PERSON_NAME", columnDefinition = "VARCHAR(45) NOT NULL" , nullable = false)
	private String name;

	@Comment("사용개인전화번호")
	@Column(name = "USE_PERSON_PNBR", columnDefinition = "VARCHAR(18) NOT NULL" , nullable = false)
	private String phoneNumber;

	public UsePersonDto.Response toDto() {
		return UsePersonDto.Response.builder()
			.id(this.id)
			.name(this.name)
			.phoneNumber(this.phoneNumber)
			.build();
	}
}
