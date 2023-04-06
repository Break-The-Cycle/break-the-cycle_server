package brave.btc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "use_person")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Column(name = "pnbr")
    private String phoneNumber;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    @JsonIgnore
//    private Address address;

    @Column(name = "lgnidn")
    private String loginId;

    private String password;

}
