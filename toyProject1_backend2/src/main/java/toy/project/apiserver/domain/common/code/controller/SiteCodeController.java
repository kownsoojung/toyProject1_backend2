package toy.project.apiserver.domain.common.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import toy.project.apiserver.domain.common.code.dto.SiteCodeDTO;
import toy.project.apiserver.domain.common.code.dto.SiteCodeSearchDTO;
import toy.project.apiserver.domain.common.code.service.SiteCodeService;

@RestController
@RequestMapping("/api/common/code")
@RequiredArgsConstructor
public class SiteCodeController {
	
	private final SiteCodeService service;
	@GetMapping("/getList")
    public List<SiteCodeDTO> getList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute SiteCodeSearchDTO dto) {
        
        List<SiteCodeDTO> result = service.getCode(dto);

        return result;
    }
}
