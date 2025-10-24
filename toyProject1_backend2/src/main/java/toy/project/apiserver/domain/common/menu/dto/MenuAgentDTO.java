package toy.project.apiserver.domain.common.menu.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuAgentDTO {

    private int id;
    private int centerId;
    private int upperId;
    private int seq;
    private String name;
    private String path;
    private int depth;
    private int useFlag;
    private int isLeaf;
}
