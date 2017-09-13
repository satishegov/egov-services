package org.egov.egf.bill.web.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailSearchContract extends BillDetailContract {
	private String ids;
	private String sortBy; 
	private Integer pageSize; 
	private Integer offset; 
}
