package brave.btc.domain.common.submissionrecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCounselingPersonSubmissionId is a Querydsl query type for CounselingPersonSubmissionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCounselingPersonSubmissionId extends BeanPath<CounselingPersonSubmissionId> {

    private static final long serialVersionUID = 285154390L;

    public static final QCounselingPersonSubmissionId counselingPersonSubmissionId = new QCounselingPersonSubmissionId("counselingPersonSubmissionId");

    public final NumberPath<Integer> managePersonId = createNumber("managePersonId", Integer.class);

    public final NumberPath<Integer> submissionRecordId = createNumber("submissionRecordId", Integer.class);

    public QCounselingPersonSubmissionId(String variable) {
        super(CounselingPersonSubmissionId.class, forVariable(variable));
    }

    public QCounselingPersonSubmissionId(Path<? extends CounselingPersonSubmissionId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCounselingPersonSubmissionId(PathMetadata metadata) {
        super(CounselingPersonSubmissionId.class, metadata);
    }

}

