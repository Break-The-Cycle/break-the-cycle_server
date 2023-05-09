package brave.btc.domain.jwt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken {

    @Comment("리프레시 토큰 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRESH_TOKEN_ID", columnDefinition = "INT NOT NULL")
    private Integer id;

    @Comment("리프레시 토큰 정보")
    @Column(name = "REFRESH_TOKEN_INFO", nullable = false, unique = true)
    private String token;

    @Comment("유저 전화번호 정보")
    @Column(name = "REFRESH_TOKEN_USE_PERSON_PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    public void changeToken(String token) {
        this.token = token;
    }
}
