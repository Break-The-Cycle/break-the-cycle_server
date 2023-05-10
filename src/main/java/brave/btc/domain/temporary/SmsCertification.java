package brave.btc.domain.temporary;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sms_certification")
public class SmsCertification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
