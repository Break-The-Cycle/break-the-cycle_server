package brave.btc.domain.bo.submissionrecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCounselingPersonSubmission is a Querydsl query type for CounselingPersonSubmission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCounselingPersonSubmission extends EntityPathBase<CounselingPersonSubmission> {

    private static final long serialVersionUID = 1451351229L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCounselingPersonSubmission counselingPersonSubmission = new QCounselingPersonSubmission("counselingPersonSubmission");

    public final QCounselingPersonSubmissionId id;

    public final brave.btc.domain.bo.user.QManagePerson managePerson;

    public final QSubmissionRecord submissionRecord;

    public QCounselingPersonSubmission(String variable) {
        this(CounselingPersonSubmission.class, forVariable(variable), INITS);
    }

    public QCounselingPersonSubmission(Path<? extends CounselingPersonSubmission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCounselingPersonSubmission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCounselingPersonSubmission(PathMetadata metadata, PathInits inits) {
        this(CounselingPersonSubmission.class, metadata, inits);
    }

    public QCounselingPersonSubmission(Class<? extends CounselingPersonSubmission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCounselingPersonSubmissionId(forProperty("id")) : null;
        this.managePerson = inits.isInitialized("managePerson") ? new brave.btc.domain.bo.user.QManagePerson(forProperty("managePerson"), inits.get("managePerson")) : null;
        this.submissionRecord = inits.isInitialized("submissionRecord") ? new QSubmissionRecord(forProperty("submissionRecord"), inits.get("submissionRecord")) : null;
    }

}

