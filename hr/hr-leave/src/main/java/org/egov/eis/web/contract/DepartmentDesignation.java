package org.egov.eis.web.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentDesignation {

    private Long id;

    @JsonProperty("department")
    private Long departmentId;

    private Designation designation;

}
