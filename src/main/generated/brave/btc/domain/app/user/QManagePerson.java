package brave.btc.domain.app.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManagePerson is a Querydsl query type for ManagePerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManagePerson extends EntityPathBase<ManagePerson> {

    private static final long serialVersionUID = 1242475633L;

    public static final QManagePerson managePerson = new QManagePerson("managePerson");

    public final QUser _super = new QUser(this);

    public final StringPath division = createString("division");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public QManagePerson(String variable) {
        super(ManagePerson.class, forVariable(variable));
    }

    public QManagePerson(Path<? extends ManagePerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManagePerson(PathMetadata metadata) {
        super(ManagePerson.class, metadata);
    }

}

