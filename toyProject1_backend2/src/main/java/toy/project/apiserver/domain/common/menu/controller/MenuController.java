package toy.project.apiserver.domain.common.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import toy.project.apiserver.domain.common.menu.dto.MenuAgentDTO;
import toy.project.apiserver.domain.common.menu.service.MenuService;

@RestController
@RequestMapping("/api/common/menu")
public class MenuController {

    @Autowired
    MenuService service;

    @GetMapping("/getList")
    public List<MenuAgentDTO> getList() {
        
        List<MenuAgentDTO> result = service.getList();

        return result;
    }
}
