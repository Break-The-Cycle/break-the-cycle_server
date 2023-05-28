package brave.btc.domain.common.submissionrecord;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPolicePersonSubmissionId is a Querydsl query type for PolicePersonSubmissionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPolicePersonSubmissionId extends BeanPath<PolicePersonSubmissionId> {

    private static final long serialVersionUID = -1914863577L;

    public static final QPolicePersonSubmissionId policePersonSubmissionId = new QPolicePersonSubmissionId("policePersonSubmissionId");

    public final NumberPath<Integer> managePersonId = createNumber("managePersonId", Integer.class);

    public final NumberPath<Integer> submissionRecordId = createNumber("submissionRecordId", Integer.class);

    public QPolicePersonSubmissionId(String variable) {
        super(PolicePersonSubmissionId.class, forVariable(variable));
    }

    public QPolicePersonSubmissionId(Path<? extends PolicePersonSubmissionId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPolicePersonSubmissionId(PathMetadata metadata) {
        super(PolicePersonSubmissionId.class, metadata);
    }

}

