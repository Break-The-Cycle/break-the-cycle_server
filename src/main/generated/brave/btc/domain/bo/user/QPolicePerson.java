package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPolicePerson is a Querydsl query type for PolicePerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPolicePerson extends EntityPathBase<PolicePerson> {

    private static final long serialVersionUID = 1109088116L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPolicePerson policePerson = new QPolicePerson("policePerson");

    public final QManagePerson _super = new QManagePerson(this);

    public final StringPath department = createString("department");

    //inherited
    public final EnumPath<brave.btc.constant.enums.ManageDivision> division = _super.division;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    //inherited
    public final StringPath loginId = _super.loginId;

    //inherited
    public final StringPath name = _super.name;

    public final QOfficialInstitution officialInstitution;

    //inherited
    public final StringPath password = _super.password;

    public final StringPath position = createString("position");

    public QPolicePerson(String variable) {
        this(PolicePerson.class, forVariable(variable), INITS);
    }

    public QPolicePerson(Path<? extends PolicePerson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPolicePerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPolicePerson(PathMetadata metadata, PathInits inits) {
        this(PolicePerson.class, metadata, inits);
    }

    public QPolicePerson(Class<? extends PolicePerson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.officialInstitution = inits.isInitialized("officialInstitution") ? new QOfficialInstitution(forProperty("officialInstitution")) : null;
    }

}

