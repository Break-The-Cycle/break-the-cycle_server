package brave.btc.domain.app.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecording is a Querydsl query type for Recording
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecording extends EntityPathBase<Recording> {

    private static final long serialVersionUID = -1481028608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecording recording = new QRecording("recording");

    public final QRecord _super;

    public final StringPath content = createString("content");

    public final StringPath conversionContent = createString("conversionContent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Integer> id;

    //inherited
    public final EnumPath<brave.btc.constant.enums.RecordDivision> recordDivision;

    //inherited
    public final DatePath<java.time.LocalDate> reportDate;

    // inherited
    public final brave.btc.domain.app.user.QUsePerson usePerson;

    public QRecording(String variable) {
        this(Recording.class, forVariable(variable), INITS);
    }

    public QRecording(Path<? extends Recording> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecording(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecording(PathMetadata metadata, PathInits inits) {
        this(Recording.class, metadata, inits);
    }

    public QRecording(Class<? extends Recording> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRecord(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.recordDivision = _super.recordDivision;
        this.reportDate = _super.reportDate;
        this.usePerson = _super.usePerson;
    }

}

