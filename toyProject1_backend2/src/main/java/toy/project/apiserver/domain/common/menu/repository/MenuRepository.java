package toy.project.apiserver.domain.common.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toy.project.apiserver.domain.common.menu.entity.MenuAgentEntity;

public interface MenuRepository extends JpaRepository<MenuAgentEntity, Integer> {

}
