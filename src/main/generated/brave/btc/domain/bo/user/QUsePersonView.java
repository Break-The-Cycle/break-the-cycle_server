package brave.btc.domain.bo.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsePersonView is a Querydsl query type for UsePersonView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsePersonView extends EntityPathBase<UsePersonView> {

    private static final long serialVersionUID = -1770688704L;

    public static final QUsePersonView usePersonView = new QUsePersonView("usePersonView");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QUsePersonView(String variable) {
        super(UsePersonView.class, forVariable(variable));
    }

    public QUsePersonView(Path<? extends UsePersonView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsePersonView(PathMetadata metadata) {
        super(UsePersonView.class, metadata);
    }

}

