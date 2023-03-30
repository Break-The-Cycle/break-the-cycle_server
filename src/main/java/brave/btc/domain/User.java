package brave.btc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.openid.connect.sdk.claims.Address;
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
    private String phone_number;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    @JsonIgnore
//    private Address address;

    @Column(name = "lgnidn")
    private String login_id;
    private String password;

    public User() {
    }

    public User(String name, String phone_number, String login_id, String password) {
        this.name = name;
        this.phone_number = phone_number;
        this.login_id = login_id;
        this.password = password;
    }
}
