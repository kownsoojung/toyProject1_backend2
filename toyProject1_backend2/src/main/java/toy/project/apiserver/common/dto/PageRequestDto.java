package toy.project.apiserver.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
	private int page=1;
	private int pageSize=10;
	private String sortOrder;
	private String sortColumn;
	
	public int getOffset() {
        return (page - 1) * pageSize;
    }

}
