package brave.btc.domain.app.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPicture is a Querydsl query type for Picture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPicture extends EntityPathBase<Picture> {

    private static final long serialVersionUID = 1757110989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPicture picture = new QPicture("picture");

    public final QRecord _super;

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

    public QPicture(String variable) {
        this(Picture.class, forVariable(variable), INITS);
    }

    public QPicture(Path<? extends Picture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPicture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPicture(PathMetadata metadata, PathInits inits) {
        this(Picture.class, metadata, inits);
    }

    public QPicture(Class<? extends Picture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRecord(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.recordDivision = _super.recordDivision;
        this.reportDate = _super.reportDate;
        this.usePerson = _super.usePerson;
    }

}

