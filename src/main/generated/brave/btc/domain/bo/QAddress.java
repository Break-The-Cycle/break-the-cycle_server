package brave.btc.domain.bo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddress extends EntityPathBase<Address> {

    private static final long serialVersionUID = -1179312814L;

    public static final QAddress address = new QAddress("address");

    public final StringPath bungee = createString("bungee");

    public final EnumPath<brave.btc.constant.enums.AddressDivision> division = createEnum("division", brave.btc.constant.enums.AddressDivision.class);

    public final StringPath eupmyeondong = createString("eupmyeondong");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath island = createString("island");

    public final StringPath li = createString("li");

    public final StringPath postalNumber = createString("postalNumber");

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

