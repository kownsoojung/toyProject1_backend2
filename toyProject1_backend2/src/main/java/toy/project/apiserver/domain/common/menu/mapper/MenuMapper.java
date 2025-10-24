package toy.project.apiserver.domain.common.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import toy.project.apiserver.domain.common.menu.dto.MenuAgentDTO;

@Mapper
public interface MenuMapper {

    public List<MenuAgentDTO> getList();

}
