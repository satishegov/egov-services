package org.egov.demand.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.egov.demand.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxHeadMaster {
		
		  
		  private String id;

		  @NotNull
		  private String tenantId;
		  @Valid
		  @NotNull
		  private Category category;
		  @NotNull
		  private String service;
		  @NotNull
		  private String name;

		  private String code;

		  private String glCode;

		  private Boolean isDebit = false;

		  private Boolean isActualDemand;
		  
		  private TaxPeriod taxPeriod = null;

		  private AuditDetail auditDetail;
}
