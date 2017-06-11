/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.pgr.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.pgr.model.Attribute;
import org.egov.pgr.model.ServiceType;
import org.egov.pgr.model.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeRowMapper implements RowMapper<ServiceType> {
	public static Map<String, List<Value>> attribValue = new HashMap<>();
	public static Map<String, List<Attribute>> serviceAttrib = new HashMap<>();
	public static Map<String, ServiceType> serviceMap = new HashMap<>();
    @Override
    public ServiceType mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    	if(serviceMap.containsKey(rs.getString("code"))){
    		ServiceType serviceType= serviceMap.get(rs.getString("code"));
    		if(serviceAttrib.containsKey(rs.getString("code"))){
    			List<Attribute> attributeList = serviceAttrib.get(rs.getString("code"));
    			Attribute attribute = new Attribute();
    			attribute.setCode(rs.getString("attributecode"));
    		} else {
    			
    		}
    	} else {
    		ServiceType serviceType = new ServiceType();
    		serviceType.setServiceName(rs.getString("name"));
    		serviceType.setServiceCode(rs.getString("code"));
    		serviceType.setTenantId(rs.getString("tenantid"));
    		serviceType.setDescription(rs.getString("description"));
    		serviceMap.put(rs.getString("code"), serviceType);
    	}
        return new ServiceType();
    }
}
