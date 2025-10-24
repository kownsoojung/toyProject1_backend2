package toy.project.apiserver.domain.common.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefreshTokenEntity is a Querydsl query type for RefreshTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshTokenEntity extends EntityPathBase<RefreshTokenEntity> {

    private static final long serialVersionUID = -2076013256L;

    public static final QRefreshTokenEntity refreshTokenEntity = new QRefreshTokenEntity("refreshTokenEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final BooleanPath revoked = createBoolean("revoked");

    public final StringPath token = createString("token");

    public final NumberPath<Long> tokenId = createNumber("tokenId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QRefreshTokenEntity(String variable) {
        super(RefreshTokenEntity.class, forVariable(variable));
    }

    public QRefreshTokenEntity(Path<? extends RefreshTokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefreshTokenEntity(PathMetadata metadata) {
        super(RefreshTokenEntity.class, metadata);
    }

}

