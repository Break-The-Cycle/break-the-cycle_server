package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManagePerson is a Querydsl query type for ManagePerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManagePerson extends EntityPathBase<ManagePerson> {

    private static final long serialVersionUID = -327631237L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManagePerson managePerson = new QManagePerson("managePerson");

    public final brave.btc.domain.common.user.QUser _super = new brave.btc.domain.common.user.QUser(this);

    public final brave.btc.domain.bo.QAddress address;

    public final EnumPath<brave.btc.constant.enums.ManageDivision> division = createEnum("division", brave.btc.constant.enums.ManageDivision.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath isAccountNonExpired = createBoolean("isAccountNonExpired");

    public final BooleanPath isAccountNonLocked = createBoolean("isAccountNonLocked");

    public final BooleanPath isCredentialsNonExpired = createBoolean("isCredentialsNonExpired");

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QManagePerson(String variable) {
        this(ManagePerson.class, forVariable(variable), INITS);
    }

    public QManagePerson(Path<? extends ManagePerson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManagePerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManagePerson(PathMetadata metadata, PathInits inits) {
        this(ManagePerson.class, metadata, inits);
    }

    public QManagePerson(Class<? extends ManagePerson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new brave.btc.domain.bo.QAddress(forProperty("address")) : null;
    }

}

