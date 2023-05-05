package brave.btc.domain.temporary;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSmsCertification is a Querydsl query type for SmsCertification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmsCertification extends EntityPathBase<SmsCertification> {

    private static final long serialVersionUID = -1453702627L;

    public static final QSmsCertification smsCertification = new QSmsCertification("smsCertification");

    public final StringPath certificationNumber = createString("certificationNumber");

    public final DateTimePath<java.sql.Timestamp> created = createDateTime("created", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public QSmsCertification(String variable) {
        super(SmsCertification.class, forVariable(variable));
    }

    public QSmsCertification(Path<? extends SmsCertification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSmsCertification(PathMetadata metadata) {
        super(SmsCertification.class, metadata);
    }

}

