package brave.btc.domain.app.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsePerson is a Querydsl query type for UsePerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsePerson extends EntityPathBase<UsePerson> {

    private static final long serialVersionUID = -2146305083L;

    public static final QUsePerson usePerson = new QUsePerson("usePerson");

    public final brave.btc.domain.common.user.QUser _super = new brave.btc.domain.common.user.QUser(this);

    public final StringPath emergencyReportContent = createString("emergencyReportContent");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath loginId = createString("loginId");

    public final SimplePath<java.time.Period> menstruationPeriod = createSimple("menstruationPeriod", java.time.Period.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QUsePerson(String variable) {
        super(UsePerson.class, forVariable(variable));
    }

    public QUsePerson(Path<? extends UsePerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsePerson(PathMetadata metadata) {
        super(UsePerson.class, metadata);
    }

}

