package brave.btc.domain.app.address;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressName is a Querydsl query type for AddressName
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddressName extends EntityPathBase<AddressName> {

    private static final long serialVersionUID = -2008874375L;

    public static final QAddressName addressName = new QAddressName("addressName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public QAddressName(String variable) {
        super(AddressName.class, forVariable(variable));
    }

    public QAddressName(Path<? extends AddressName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressName(PathMetadata metadata) {
        super(AddressName.class, metadata);
    }

}

