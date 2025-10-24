package toy.project.apiserver.domain.common.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.project.apiserver.domain.common.code.entity.SiteCodeEntity;

public interface SiteCodeRepository extends JpaRepository<SiteCodeEntity, Integer> {

	

}
