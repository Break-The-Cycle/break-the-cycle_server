package brave.btc.domain.bo.user;

import brave.btc.constant.enums.ManageDivision;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue(ManageDivision.Values.BACKOFFICE_MANAGE_PERSON)
@Table(name = "BACKOFFICE_MANAGE_PERSON")
public class BackOfficeManagePerson extends ManagePerson{

}
