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

    public final QManagePerson _super = new QManagePerson(this);

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
        this.officialInstitution = inits.isInitialized("officialInstitution") ? new QOfficialInstitution(forProperty("officialInstitution")) : null;
    }

}

