package brave.btc.domain.app.submission_record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubmissionRecording is a Querydsl query type for SubmissionRecording
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubmissionRecording extends EntityPathBase<SubmissionRecording> {

    private static final long serialVersionUID = -485614705L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubmissionRecording submissionRecording = new QSubmissionRecording("submissionRecording");

    public final QUsePersonSubmissionRecord _super;

    public final StringPath content = createString("content");

    public final StringPath conversionContent = createString("conversionContent");

    //inherited
    public final NumberPath<Integer> id;

    //inherited
    public final DatePath<java.time.LocalDate> recordDate;

    //inherited
    public final EnumPath<brave.btc.constant.enums.RecordDivision> recordDivision;

    // inherited
    public final QSubmissionRecord submissionRecord;

    public QSubmissionRecording(String variable) {
        this(SubmissionRecording.class, forVariable(variable), INITS);
    }

    public QSubmissionRecording(Path<? extends SubmissionRecording> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubmissionRecording(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubmissionRecording(PathMetadata metadata, PathInits inits) {
        this(SubmissionRecording.class, metadata, inits);
    }

    public QSubmissionRecording(Class<? extends SubmissionRecording> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QUsePersonSubmissionRecord(type, metadata, inits);
        this.id = _super.id;
        this.recordDate = _super.recordDate;
        this.recordDivision = _super.recordDivision;
        this.submissionRecord = _super.submissionRecord;
    }

}

