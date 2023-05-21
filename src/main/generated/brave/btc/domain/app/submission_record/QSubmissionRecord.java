package brave.btc.domain.app.submission_record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubmissionRecord is a Querydsl query type for SubmissionRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubmissionRecord extends EntityPathBase<SubmissionRecord> {

    private static final long serialVersionUID = 1700324211L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubmissionRecord submissionRecord = new QSubmissionRecord("submissionRecord");

    public final DateTimePath<java.time.LocalDateTime> effectiveDatetime = createDateTime("effectiveDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> submissionDatetime = createDateTime("submissionDatetime", java.time.LocalDateTime.class);

    public final EnumPath<brave.btc.constant.enums.SubmissionDivision> submissionDivision = createEnum("submissionDivision", brave.btc.constant.enums.SubmissionDivision.class);

    public final brave.btc.domain.app.user.QUsePerson usePerson;

    public QSubmissionRecord(String variable) {
        this(SubmissionRecord.class, forVariable(variable), INITS);
    }

    public QSubmissionRecord(Path<? extends SubmissionRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubmissionRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubmissionRecord(PathMetadata metadata, PathInits inits) {
        this(SubmissionRecord.class, metadata, inits);
    }

    public QSubmissionRecord(Class<? extends SubmissionRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usePerson = inits.isInitialized("usePerson") ? new brave.btc.domain.app.user.QUsePerson(forProperty("usePerson")) : null;
    }

}

