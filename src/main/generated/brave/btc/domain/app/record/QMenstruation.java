package brave.btc.domain.app.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenstruation is a Querydsl query type for Menstruation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenstruation extends EntityPathBase<Menstruation> {

    private static final long serialVersionUID = -1508335732L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenstruation menstruation = new QMenstruation("menstruation");

    public final QRecord _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> datetime;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    //inherited
    public final NumberPath<Integer> id;

    public final SimplePath<java.time.Period> period = createSimple("period", java.time.Period.class);

    //inherited
    public final EnumPath<brave.btc.constant.enums.RecordDivision> recordDivision;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    // inherited
    public final brave.btc.domain.app.user.QUsePerson usePerson;

    public QMenstruation(String variable) {
        this(Menstruation.class, forVariable(variable), INITS);
    }

    public QMenstruation(Path<? extends Menstruation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenstruation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenstruation(PathMetadata metadata, PathInits inits) {
        this(Menstruation.class, metadata, inits);
    }

    public QMenstruation(Class<? extends Menstruation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRecord(type, metadata, inits);
        this.datetime = _super.datetime;
        this.id = _super.id;
        this.recordDivision = _super.recordDivision;
        this.usePerson = _super.usePerson;
    }

}

