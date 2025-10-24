package toy.project.apiserver.domain.common.code.entity;



import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sitecode")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteCodeEntity {
	@Id
	private int uidx;
	private int centerId;
	private int tenantId;
	private String codeKind;
	private String codeName;
	private int codeNumber;
	private String codeValue;
	private String codeDesc;
	private int codeSeq;
	private String codeLang;
	private int useFlag;
	private String updateEmployeeId;
	private Date updateTime;

}
