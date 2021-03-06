/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.eis.repository.rowmapper;

import org.egov.eis.model.LeaveApplication;
import org.egov.eis.model.LeaveType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class LeaveApplicationRowMapper implements RowMapper<LeaveApplication> {

	@Override
	public LeaveApplication mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		final LeaveApplication leaveApplication = new LeaveApplication();
		leaveApplication.setId(rs.getLong("la_id"));
		leaveApplication.setApplicationNumber(rs.getString("la_applicationNumber"));
		leaveApplication.setEmployee((Long) rs.getObject("la_employeeId"));

		final LeaveType leaveType = new LeaveType();
		leaveType.setId(rs.getLong("lt_id"));
		leaveType.setName(rs.getString("lt_name"));
		leaveType.setDescription(rs.getString("lt_description"));
		leaveType.setHalfdayAllowed((Boolean) rs.getObject("lt_halfdayAllowed"));
		leaveType.setPayEligible((Boolean) rs.getObject("lt_payEligible"));
		leaveType.setAccumulative((Boolean) rs.getObject("lt_accumulative"));
		leaveType.setEncashable((Boolean) rs.getObject("lt_encashable"));
		leaveType.setActive((Boolean) rs.getObject("lt_active"));
		leaveType.setCreatedBy((Long) rs.getObject("lt_createdBy"));
		leaveType.setLastModifiedBy((Long) rs.getObject("lt_lastModifiedBy"));
		leaveType.setTenantId(rs.getString("la_tenantId"));
		try {
			Date date = isEmpty(rs.getDate("lt_createdDate")) ? null
					: sdf.parse(sdf.format(rs.getDate("lt_createdDate")));
			leaveType.setCreatedDate(date);
			date = isEmpty(rs.getDate("lt_lastModifiedDate")) ? null
					: sdf.parse(sdf.format(rs.getDate("lt_lastModifiedDate")));
			leaveType.setLastModifiedDate(date);
			date = isEmpty(rs.getDate("la_fromDate")) ? null : sdf.parse(sdf.format(rs.getDate("la_fromDate")));
			leaveApplication.setFromDate(date);
			date = isEmpty(rs.getDate("la_toDate")) ? null : sdf.parse(sdf.format(rs.getDate("la_toDate")));
			leaveApplication.setToDate(date);
			date = isEmpty(rs.getDate("la_compensatoryForDate")) ? null
					: sdf.parse(sdf.format(rs.getDate("la_compensatoryForDate")));
			leaveApplication.setCompensatoryForDate(date);
			date = isEmpty(rs.getDate("la_createdDate")) ? null : sdf.parse(sdf.format(rs.getDate("la_createdDate")));
			leaveApplication.setCreatedDate(date);
			date = isEmpty(rs.getDate("la_lastModifiedDate")) ? null
					: sdf.parse(sdf.format(rs.getDate("la_lastModifiedDate")));
			leaveApplication.setLastModifiedDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SQLException("Parse exception while parsing Date");
		}

		leaveApplication.setLeaveType(leaveType);

		leaveApplication.setLeaveDays((Float) rs.getObject("la_leaveDays"));
		leaveApplication.setAvailableDays((Float) rs.getObject("la_availableDays"));
		leaveApplication.setHalfdays((Integer) rs.getObject("la_halfdays"));
		leaveApplication.setFirstHalfleave((Boolean) rs.getObject("la_firstHalfleave"));
		leaveApplication.setReason(rs.getString("la_reason"));
		leaveApplication.setStatus(rs.getLong("la_status"));
		leaveApplication.setStateId(rs.getLong("la_stateId"));
		leaveApplication.setCreatedBy((Long) rs.getObject("la_createdBy"));
		leaveApplication.setLastModifiedBy((Long) rs.getObject("la_lastModifiedBy"));
		leaveApplication.setTenantId(rs.getString("la_tenantId"));

		return leaveApplication;
	}
}