package toy.project.apiserver.domain.common.code.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import toy.project.apiserver.domain.common.code.dto.SiteCodeDTO;
import toy.project.apiserver.domain.common.code.dto.SiteCodeSearchDTO;
import toy.project.apiserver.domain.common.code.entity.QSiteCodeEntity;


@Repository
@RequiredArgsConstructor
public class  SiteCodeQuerydslRepository  {
	
	private final JPAQueryFactory queryFactory;


	public List<SiteCodeDTO> findByCodeNameAndUseFlag(SiteCodeSearchDTO dto) {
		QSiteCodeEntity site = QSiteCodeEntity.siteCodeEntity;

        return queryFactory
                .select(Projections.bean(SiteCodeDTO.class,
                        site.codeValue,
                        site.codeNumber))
                .from(site)
                .where(site.codeName.eq(dto.getCodeName())
                .and(site.useFlag.eq(1)))
                .groupBy(site.codeNumber)
                .orderBy(site.codeSeq.asc())
                .fetch();
	}
	

}
