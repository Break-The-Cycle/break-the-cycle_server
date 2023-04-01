package brave.btc.domain;

import jakarta.persistence.*;

@Embeddable
public class Postal {

    private String zipcode;
    private String city;

}
