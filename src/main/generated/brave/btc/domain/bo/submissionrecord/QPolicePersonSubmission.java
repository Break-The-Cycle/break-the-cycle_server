package brave.btc.domain.bo.submissionrecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPolicePersonSubmission is a Querydsl query type for PolicePersonSubmission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPolicePersonSubmission extends EntityPathBase<PolicePersonSubmission> {

    private static final long serialVersionUID = -419446322L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPolicePersonSubmission policePersonSubmission = new QPolicePersonSubmission("policePersonSubmission");

    public final QPolicePersonSubmissionId id;

    public final brave.btc.domain.bo.user.QManagePerson managePerson;

    public final QSubmissionRecord submissionRecord;

    public QPolicePersonSubmission(String variable) {
        this(PolicePersonSubmission.class, forVariable(variable), INITS);
    }

    public QPolicePersonSubmission(Path<? extends PolicePersonSubmission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPolicePersonSubmission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPolicePersonSubmission(PathMetadata metadata, PathInits inits) {
        this(PolicePersonSubmission.class, metadata, inits);
    }

    public QPolicePersonSubmission(Class<? extends PolicePersonSubmission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPolicePersonSubmissionId(forProperty("id")) : null;
        this.managePerson = inits.isInitialized("managePerson") ? new brave.btc.domain.bo.user.QManagePerson(forProperty("managePerson"), inits.get("managePerson")) : null;
        this.submissionRecord = inits.isInitialized("submissionRecord") ? new QSubmissionRecord(forProperty("submissionRecord"), inits.get("submissionRecord")) : null;
    }

}

