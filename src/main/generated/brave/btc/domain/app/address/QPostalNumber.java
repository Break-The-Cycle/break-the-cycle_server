package brave.btc.domain.app.address;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostalNumber is a Querydsl query type for PostalNumber
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostalNumber extends EntityPathBase<PostalNumber> {

    private static final long serialVersionUID = -2090374822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostalNumber postalNumber = new QPostalNumber("postalNumber");

    public final QAddress address;

    public final StringPath bungee = createString("bungee");

    public final StringPath eupmyeondong = createString("eupmyeondong");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath island = createString("island");

    public final StringPath li = createString("li");

    public final StringPath number = createString("number");

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public QPostalNumber(String variable) {
        this(PostalNumber.class, forVariable(variable), INITS);
    }

    public QPostalNumber(Path<? extends PostalNumber> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostalNumber(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostalNumber(PathMetadata metadata, PathInits inits) {
        this(PostalNumber.class, metadata, inits);
    }

    public QPostalNumber(Class<? extends PostalNumber> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

