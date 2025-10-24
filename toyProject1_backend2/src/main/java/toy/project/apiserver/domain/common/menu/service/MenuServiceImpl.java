package toy.project.apiserver.domain.common.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import toy.project.apiserver.domain.common.menu.dto.MenuAgentDTO;
import toy.project.apiserver.domain.common.menu.mapper.MenuMapper;
import toy.project.apiserver.domain.common.menu.repository.MenuRepository;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService{
    
    private final MenuMapper mapper;
    private final MenuRepository repository;
    
    
    @Override
    public List<MenuAgentDTO> getList() {
        return mapper.getList();
    }

}
