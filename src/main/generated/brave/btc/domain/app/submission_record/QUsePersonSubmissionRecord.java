package brave.btc.domain.app.submission_record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsePersonSubmissionRecord is a Querydsl query type for UsePersonSubmissionRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsePersonSubmissionRecord extends EntityPathBase<UsePersonSubmissionRecord> {

    private static final long serialVersionUID = -453806429L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsePersonSubmissionRecord usePersonSubmissionRecord = new QUsePersonSubmissionRecord("usePersonSubmissionRecord");

    public final DateTimePath<java.time.LocalDateTime> effectiveDatetime = createDateTime("effectiveDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> submissionDatetime = createDateTime("submissionDatetime", java.time.LocalDateTime.class);

    public final EnumPath<brave.btc.constant.enums.SubmissionDivision> submissionDivision = createEnum("submissionDivision", brave.btc.constant.enums.SubmissionDivision.class);

    public final brave.btc.domain.app.user.QUsePerson usePerson;

    public QUsePersonSubmissionRecord(String variable) {
        this(UsePersonSubmissionRecord.class, forVariable(variable), INITS);
    }

    public QUsePersonSubmissionRecord(Path<? extends UsePersonSubmissionRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsePersonSubmissionRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsePersonSubmissionRecord(PathMetadata metadata, PathInits inits) {
        this(UsePersonSubmissionRecord.class, metadata, inits);
    }

    public QUsePersonSubmissionRecord(Class<? extends UsePersonSubmissionRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usePerson = inits.isInitialized("usePerson") ? new brave.btc.domain.app.user.QUsePerson(forProperty("usePerson")) : null;
    }

}

