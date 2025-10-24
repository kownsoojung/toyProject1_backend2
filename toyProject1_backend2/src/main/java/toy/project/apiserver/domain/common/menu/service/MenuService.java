package toy.project.apiserver.domain.common.menu.service;

import java.util.List;

import toy.project.apiserver.domain.common.menu.dto.MenuAgentDTO;
import toy.project.apiserver.domain.common.menu.entity.MenuAgentEntity;


public interface MenuService {

    List<MenuAgentDTO> getList();
    
    default MenuAgentDTO entityToDTO(MenuAgentEntity entity) {
        return MenuAgentDTO.builder()
                    .id(entity.getId())
                    .centerId(entity.getCenterId())
                    .depth(entity.getDepth())
                    .path(entity.getDescription())
                    .name(entity.getName())
                    .build();
    }

    default MenuAgentEntity dtoToEntity(MenuAgentDTO dto) {
        return MenuAgentEntity.builder()
                    .id(dto.getId())
                    .centerId(dto.getCenterId())
                    .depth(dto.getDepth())
                    .description(dto.getPath())
                    .name(dto.getName())
                    .build();
    }
}
