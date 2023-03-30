//package brave.btc.domain;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Address {
//
//    @Id @GeneratedValue
//    @Column(name = "address_id")
//    private Long id;
//    private String dvsn;
//
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private User user;
//
//    @Embedded
//    private Postal postal;
//
//}
