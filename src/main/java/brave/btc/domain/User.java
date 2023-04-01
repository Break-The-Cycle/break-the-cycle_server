package brave.btc.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@Table(name = "use_person")
@EqualsAndHashCode(of = "id")
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

    public User() {
    }

    public User(String name, String phoneNumber, String loginId, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
        this.password = password;
    }
}
