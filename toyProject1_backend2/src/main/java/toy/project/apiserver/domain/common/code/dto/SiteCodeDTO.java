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
public class SiteCodeDTO {
	private int uidx;
	private int centerId;
	private int tenantId;
	private String codeKind;
	private String codeName;
	private String label;
	private int codeNumber;
	private String codeValue;
	private String parentCodeNumber;
	private String value;
	private String codeDesc;
	private int codeSeq;
	private String codeLang;
	private int useFlag;
	private boolean disabled;
	
	// QueryDSL용 생성자
    public SiteCodeDTO(String label, String value) {
        this.label = label;
        this.value = value;
    }
    
}
