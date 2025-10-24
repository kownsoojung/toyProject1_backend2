package toy.project.apiserver.domain.common.code.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import toy.project.apiserver.domain.common.code.dto.SiteCodeDTO;
import toy.project.apiserver.domain.common.code.dto.SiteCodeSearchDTO;
import toy.project.apiserver.domain.common.code.repository.SiteCodeQuerydslRepository;
import toy.project.apiserver.domain.common.code.repository.SiteCodeRepository;
import toy.project.apiserver.domain.common.menu.mapper.MenuMapper;
import toy.project.apiserver.domain.common.menu.repository.MenuRepository;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SiteCodeServiceImpl implements SiteCodeService {
	
	private final SiteCodeRepository repository;
	private final SiteCodeQuerydslRepository querydslRepository;
	@Override
	public List<SiteCodeDTO> getCode(SiteCodeSearchDTO dto) {
		// TODO Auto-generated method stub
		return querydslRepository.findByCodeNameAndUseFlag(dto);
	}

}
