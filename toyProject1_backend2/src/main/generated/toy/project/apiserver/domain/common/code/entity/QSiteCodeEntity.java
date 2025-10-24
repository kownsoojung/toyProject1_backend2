package toy.project.apiserver.domain.common.code.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSiteCodeEntity is a Querydsl query type for SiteCodeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSiteCodeEntity extends EntityPathBase<SiteCodeEntity> {

    private static final long serialVersionUID = -191030576L;

    public static final QSiteCodeEntity siteCodeEntity = new QSiteCodeEntity("siteCodeEntity");

    public final NumberPath<Integer> centerId = createNumber("centerId", Integer.class);

    public final StringPath codeDesc = createString("codeDesc");

    public final StringPath codeKind = createString("codeKind");

    public final StringPath codeLang = createString("codeLang");

    public final StringPath codeName = createString("codeName");

    public final NumberPath<Integer> codeNumber = createNumber("codeNumber", Integer.class);

    public final NumberPath<Integer> codeSeq = createNumber("codeSeq", Integer.class);

    public final StringPath codeValue = createString("codeValue");

    public final NumberPath<Integer> tenantId = createNumber("tenantId", Integer.class);

    public final NumberPath<Integer> uidx = createNumber("uidx", Integer.class);

    public final StringPath updateEmployeeId = createString("updateEmployeeId");

    public final DatePath<java.sql.Date> updateTime = createDate("updateTime", java.sql.Date.class);

    public final NumberPath<Integer> useFlag = createNumber("useFlag", Integer.class);

    public QSiteCodeEntity(String variable) {
        super(SiteCodeEntity.class, forVariable(variable));
    }

    public QSiteCodeEntity(Path<? extends SiteCodeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSiteCodeEntity(PathMetadata metadata) {
        super(SiteCodeEntity.class, metadata);
    }

}

