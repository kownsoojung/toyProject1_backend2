package toy.project.apiserver.domain.common.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuAgentEntity is a Querydsl query type for MenuAgentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuAgentEntity extends EntityPathBase<MenuAgentEntity> {

    private static final long serialVersionUID = -734744610L;

    public static final QMenuAgentEntity menuAgentEntity = new QMenuAgentEntity("menuAgentEntity");

    public final NumberPath<Integer> centerId = createNumber("centerId", Integer.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath menuClass = createString("menuClass");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> pageType = createNumber("pageType", Integer.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath updateEmployeeId = createString("updateEmployeeId");

    public final DatePath<java.sql.Date> updateTime = createDate("updateTime", java.sql.Date.class);

    public final NumberPath<Integer> upperId = createNumber("upperId", Integer.class);

    public final NumberPath<Integer> useFlag = createNumber("useFlag", Integer.class);

    public QMenuAgentEntity(String variable) {
        super(MenuAgentEntity.class, forVariable(variable));
    }

    public QMenuAgentEntity(Path<? extends MenuAgentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuAgentEntity(PathMetadata metadata) {
        super(MenuAgentEntity.class, metadata);
    }

}

