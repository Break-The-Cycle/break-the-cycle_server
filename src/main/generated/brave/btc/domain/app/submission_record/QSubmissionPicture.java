package brave.btc.domain.app.submission_record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubmissionPicture is a Querydsl query type for SubmissionPicture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubmissionPicture extends EntityPathBase<SubmissionPicture> {

    private static final long serialVersionUID = -489895396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubmissionPicture submissionPicture = new QSubmissionPicture("submissionPicture");

    public final brave.btc.domain.app.record.QRecord _super;

    public final StringPath content = createString("content");

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

    public QSubmissionPicture(String variable) {
        this(SubmissionPicture.class, forVariable(variable), INITS);
    }

    public QSubmissionPicture(Path<? extends SubmissionPicture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubmissionPicture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubmissionPicture(PathMetadata metadata, PathInits inits) {
        this(SubmissionPicture.class, metadata, inits);
    }

    public QSubmissionPicture(Class<? extends SubmissionPicture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new brave.btc.domain.app.record.QRecord(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.recordDivision = _super.recordDivision;
        this.reportDate = _super.reportDate;
        this.usePerson = _super.usePerson;
    }

}

