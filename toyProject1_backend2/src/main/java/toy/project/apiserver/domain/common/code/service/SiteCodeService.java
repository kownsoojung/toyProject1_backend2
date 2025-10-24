package toy.project.apiserver.domain.common.code.service;

import java.util.List;

import toy.project.apiserver.domain.common.code.dto.SiteCodeDTO;
import toy.project.apiserver.domain.common.code.dto.SiteCodeSearchDTO;

public interface SiteCodeService {

	List<SiteCodeDTO> getCode(SiteCodeSearchDTO dto);

}
