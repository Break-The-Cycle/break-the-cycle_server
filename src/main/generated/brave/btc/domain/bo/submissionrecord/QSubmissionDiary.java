package brave.btc.domain.bo.submissionrecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubmissionDiary is a Querydsl query type for SubmissionDiary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubmissionDiary extends EntityPathBase<SubmissionDiary> {

    private static final long serialVersionUID = -1987558744L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubmissionDiary submissionDiary = new QSubmissionDiary("submissionDiary");

    public final QUsePersonSubmissionRecord _super;

    public final StringPath content = createString("content");

    //inherited
    public final NumberPath<Integer> id;

    //inherited
    public final DatePath<java.time.LocalDate> recordDate;

    //inherited
    public final EnumPath<brave.btc.constant.enums.RecordDivision> recordDivision;

    // inherited
    public final QSubmissionRecord submissionRecord;

    public QSubmissionDiary(String variable) {
        this(SubmissionDiary.class, forVariable(variable), INITS);
    }

    public QSubmissionDiary(Path<? extends SubmissionDiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubmissionDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubmissionDiary(PathMetadata metadata, PathInits inits) {
        this(SubmissionDiary.class, metadata, inits);
    }

    public QSubmissionDiary(Class<? extends SubmissionDiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QUsePersonSubmissionRecord(type, metadata, inits);
        this.id = _super.id;
        this.recordDate = _super.recordDate;
        this.recordDivision = _super.recordDivision;
        this.submissionRecord = _super.submissionRecord;
    }

}

