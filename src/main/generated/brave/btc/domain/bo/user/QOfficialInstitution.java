package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOfficialInstitution is a Querydsl query type for OfficialInstitution
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOfficialInstitution extends EntityPathBase<OfficialInstitution> {

    private static final long serialVersionUID = 1818044748L;

    public static final QOfficialInstitution officialInstitution = new QOfficialInstitution("officialInstitution");

    public final EnumPath<brave.btc.constant.enums.OfficialInstitutionDivision> code = createEnum("code", brave.btc.constant.enums.OfficialInstitutionDivision.class);

    public final TimePath<java.sql.Time> endTime = createTime("endTime", java.sql.Time.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final TimePath<java.sql.Time> startTime = createTime("startTime", java.sql.Time.class);

    public QOfficialInstitution(String variable) {
        super(OfficialInstitution.class, forVariable(variable));
    }

    public QOfficialInstitution(Path<? extends OfficialInstitution> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOfficialInstitution(PathMetadata metadata) {
        super(OfficialInstitution.class, metadata);
    }

}

