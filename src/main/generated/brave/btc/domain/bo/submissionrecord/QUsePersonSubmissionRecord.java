package brave.btc.domain.bo.submissionrecord;

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

    private static final long serialVersionUID = 1503349802L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsePersonSubmissionRecord usePersonSubmissionRecord = new QUsePersonSubmissionRecord("usePersonSubmissionRecord");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DatePath<java.time.LocalDate> recordDate = createDate("recordDate", java.time.LocalDate.class);

    public final EnumPath<brave.btc.constant.enums.RecordDivision> recordDivision = createEnum("recordDivision", brave.btc.constant.enums.RecordDivision.class);

    public final QSubmissionRecord submissionRecord;

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
        this.submissionRecord = inits.isInitialized("submissionRecord") ? new QSubmissionRecord(forProperty("submissionRecord"), inits.get("submissionRecord")) : null;
    }

}

