package brave.btc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sms_certification")
public class SmsCertification {

    @Id @GeneratedValue
    @Column(name = "sms_certification_id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "certification_number")
    private String certificationNumber;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp created;


}
