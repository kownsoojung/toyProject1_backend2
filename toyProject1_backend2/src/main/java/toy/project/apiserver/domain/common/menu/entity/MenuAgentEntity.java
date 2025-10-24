package toy.project.apiserver.domain.common.menu.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "menu_agent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuAgentEntity {
    @Id
    private int id;
    private int centerId;
    private int upperId;
    private int seq;
    private String name;
    private String menuClass;
    private int pageType;
    private String description;
    private int depth;
    private int useFlag;
    private String updateEmployeeId;
    private Date updateTime;

    /* 
     * CREATE TABLE menu_agent (
    id                 NUMBER(10)       PRIMARY KEY,
    center_id          NUMBER(10)       NOT NULL,
    upper_id           NUMBER(10),
    seq                NUMBER(10),
    name               VARCHAR2(100 CHAR) NOT NULL,
    menu_class         VARCHAR2(50 CHAR),
    page_type          NUMBER(5),
    description        VARCHAR2(200 CHAR),
    depth              NUMBER(5),
    use_flag           NUMBER(1)        DEFAULT 1,
    update_employee_id VARCHAR2(50 CHAR),
    update_time        DATE DEFAULT SYSDATE
);
     */
}

