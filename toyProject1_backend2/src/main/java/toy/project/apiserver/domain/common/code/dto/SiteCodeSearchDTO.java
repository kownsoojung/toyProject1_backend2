package toy.project.apiserver.domain.common.code.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteCodeSearchDTO {
	private int centerId;
	private int tenantId;
	private String codeName;
	
	@Builder.Default
	private int useFlag=1;
}
