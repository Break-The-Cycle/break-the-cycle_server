package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCounselingPerson is a Querydsl query type for CounselingPerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCounselingPerson extends EntityPathBase<CounselingPerson> {

    private static final long serialVersionUID = -425531357L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCounselingPerson counselingPerson = new QCounselingPerson("counselingPerson");

    public final QManagePerson _super;

    // inherited
    public final brave.btc.domain.bo.QAddress address;

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

    public final QOfficialInstitution officialInstitution;

    //inherited
    public final StringPath password;

    //inherited
    public final StringPath phoneNumber;

    public final StringPath position = createString("position");

    public QCounselingPerson(String variable) {
        this(CounselingPerson.class, forVariable(variable), INITS);
    }

    public QCounselingPerson(Path<? extends CounselingPerson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCounselingPerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCounselingPerson(PathMetadata metadata, PathInits inits) {
        this(CounselingPerson.class, metadata, inits);
    }

    public QCounselingPerson(Class<? extends CounselingPerson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QManagePerson(type, metadata, inits);
        this.address = _super.address;
        this.division = _super.division;
        this.id = _super.id;
        this.isAccountNonExpired = _super.isAccountNonExpired;
        this.isAccountNonLocked = _super.isAccountNonLocked;
        this.isCredentialsNonExpired = _super.isCredentialsNonExpired;
        this.isEnabled = _super.isEnabled;
        this.loginId = _super.loginId;
        this.name = _super.name;
        this.officialInstitution = inits.isInitialized("officialInstitution") ? new QOfficialInstitution(forProperty("officialInstitution"), inits.get("officialInstitution")) : null;
        this.password = _super.password;
        this.phoneNumber = _super.phoneNumber;
    }

}

