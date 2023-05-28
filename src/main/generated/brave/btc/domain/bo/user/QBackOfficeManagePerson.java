package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBackOfficeManagePerson is a Querydsl query type for BackOfficeManagePerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackOfficeManagePerson extends EntityPathBase<BackOfficeManagePerson> {

    private static final long serialVersionUID = 2026014558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBackOfficeManagePerson backOfficeManagePerson = new QBackOfficeManagePerson("backOfficeManagePerson");

    public final QManagePerson _super;

    // inherited
    public final brave.btc.domain.bo.QAddress address;

    //inherited
    public final DatePath<java.time.LocalDate> createdAt;

    //inherited
    public final EnumPath<brave.btc.constant.enums.ManageDivision> division;

    //inherited
    public final NumberPath<Integer> id;

    //inherited
    public final BooleanPath isAccountNonExpired;

    //inherited
    public final BooleanPath isAccountNonLocked;

    //inherited
    public final BooleanPath isCredentialsNonExpired;

    //inherited
    public final BooleanPath isEnabled;

    //inherited
    public final StringPath loginId;

    //inherited
    public final StringPath name;

    //inherited
    public final StringPath password;

    //inherited
    public final StringPath phoneNumber;

    public QBackOfficeManagePerson(String variable) {
        this(BackOfficeManagePerson.class, forVariable(variable), INITS);
    }

    public QBackOfficeManagePerson(Path<? extends BackOfficeManagePerson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBackOfficeManagePerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBackOfficeManagePerson(PathMetadata metadata, PathInits inits) {
        this(BackOfficeManagePerson.class, metadata, inits);
    }

    public QBackOfficeManagePerson(Class<? extends BackOfficeManagePerson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QManagePerson(type, metadata, inits);
        this.address = _super.address;
        this.createdAt = _super.createdAt;
        this.division = _super.division;
        this.id = _super.id;
        this.isAccountNonExpired = _super.isAccountNonExpired;
        this.isAccountNonLocked = _super.isAccountNonLocked;
        this.isCredentialsNonExpired = _super.isCredentialsNonExpired;
        this.isEnabled = _super.isEnabled;
        this.loginId = _super.loginId;
        this.name = _super.name;
        this.password = _super.password;
        this.phoneNumber = _super.phoneNumber;
    }

}

