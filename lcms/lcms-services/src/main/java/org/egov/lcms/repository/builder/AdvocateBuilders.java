package org.egov.lcms.repository.builder;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.egov.lcms.config.PropertiesManager;
import org.egov.lcms.models.AdvocateSearchCriteria;
import org.egov.lcms.util.ConstantUtility;

/** 
 * 
 * @author			Date			eGov-JIRA ticket			Commit message
 * ---------------------------------------------------------------------------
 * Yosadhara	28th Oct 2017								Initial commit of Advocate query Builder
 * Yosadhara    31st Oct 2017								Modified Advocate search criteria condition
 * Yosadhara    01st Nov 2017                               Modified Advocate search query
 * Prasad       04th Nov 2017                               Added SELECT query to search Advocate by case code
 * Yosadhara    08th Nov 2017                               Modified ORDER BY clause based on lastmodifiedtime
 * Veswanth     14th Nov 2017                               Added search Agency api implementation
 * Prasad       15th Nov 2017                               Added individual and agencycode condition to search agency
 * Narendra     28th Nov 2017                               Fixed primary advocate issue
 * Yosadhara    21st Dec 2017                               Fixed inactive agencies' advocate issue
 */
public class AdvocateBuilders {

	static PropertiesManager propertiesManager;

	private static final String BASE_QUERY = "SELECT * from egov_lcms_advocate ";

	/**
	 * This method is to build SELECT query to serch Advocate
	 * 
	 * @param advocateSearchCriteria
	 * @param preparedStatementValues
	 * @return String
	 */
	public static String getSearchQuery(AdvocateSearchCriteria advocateSearchCriteria,
			List<Object> preparedStatementValues) {

		StringBuffer selectQuery = new StringBuffer(BASE_QUERY);
		addWhereClause(selectQuery, preparedStatementValues, advocateSearchCriteria);
		addOrderByClause(selectQuery, advocateSearchCriteria);
		addPagingClause(selectQuery, preparedStatementValues, advocateSearchCriteria);

		return selectQuery.toString();
	}

	/**
	 * This method is to append WHERE clause and condtions to SELECT Query
	 * 
	 * @param selectQuery
	 * @param preparedStatementValues
	 * @param advocateSearchCriteria
	 */
	private static void addWhereClause(StringBuffer selectQuery, List<Object> preparedStatementValues,
			AdvocateSearchCriteria advocateSearchCriteria) {

		if (advocateSearchCriteria.getCode() == null && advocateSearchCriteria.getAdvocateName() == null
				&& advocateSearchCriteria.getOrganizationName() == null && advocateSearchCriteria.getTenantId() == null
				&& advocateSearchCriteria.getIsActive() && advocateSearchCriteria.getIsIndividual() == null)
			return;

		selectQuery.append(" WHERE");
		if (advocateSearchCriteria.getTenantId() != null) {

			selectQuery.append(" tenantid =LOWER(?)");
			preparedStatementValues.add(advocateSearchCriteria.getTenantId());
		}

		if (advocateSearchCriteria.getIsIndividual() != null) {

			selectQuery.append(" AND isindividual = ?");
			preparedStatementValues.add(advocateSearchCriteria.getIsIndividual());
		}

		if (advocateSearchCriteria.getAdvocateName() != null) {

			StringJoiner advocateName = new StringJoiner("", "%", "%");
			advocateName.add(advocateSearchCriteria.getAdvocateName());
			selectQuery.append(" AND LOWER(name) LIKE ? ");
			preparedStatementValues.add(advocateName.toString().toLowerCase());
		}

		if (advocateSearchCriteria.getOrganizationName() != null) {

			StringJoiner organizationName = new StringJoiner("", "%", "%");
			organizationName.add(advocateSearchCriteria.getOrganizationName());
			selectQuery.append(" AND LOWER(organizationname) LIKE ?");
			preparedStatementValues.add(organizationName.toString().toLowerCase());
		}

		if (advocateSearchCriteria.getIsActive() != null) {

			selectQuery.append(" AND isactive = ?");
			preparedStatementValues.add(advocateSearchCriteria.getIsActive());
		}
		
		if (advocateSearchCriteria.getStatus() != null) {
			
			selectQuery.append(" AND lower(status)=?");
			preparedStatementValues.add(advocateSearchCriteria.getStatus());
		}
		
		if (advocateSearchCriteria.getCode() != null) {

			selectQuery.append(" AND code IN ("
					+ Stream.of(advocateSearchCriteria.getCode()).collect(Collectors.joining("','", "'", "'")) + ")");
		}
	}

	/**
	 * This method is to append ORDER BY clause to SELECT query
	 * 
	 * @param selectQuery
	 * @param advocateSearchCriteria
	 */
	private static void addOrderByClause(StringBuffer selectQuery, AdvocateSearchCriteria advocateSearchCriteria) {

		selectQuery.append(" ORDER BY ");

		if (advocateSearchCriteria.getSort() != null && advocateSearchCriteria.getSort().length > 0) {

			int orderBycount = 1;
			StringBuffer orderByCondition = new StringBuffer();

			for (String order : advocateSearchCriteria.getSort()) {
				if (orderBycount < advocateSearchCriteria.getSort().length)
					orderByCondition.append(order + ",");
				else
					orderByCondition.append(order);
				orderBycount++;
			}

			if (orderBycount > 1)
				orderByCondition.append(" desc");

			selectQuery.append(orderByCondition.toString());
		}

		selectQuery.append(" lastmodifiedtime desc ");
	}

	/**
	 * This method is to append offset, and limit to SELECT query
	 * 
	 * @param selectQuery
	 * @param preparedStatementValues
	 * @param advocateSearchCriteria
	 */
	private static void addPagingClause(StringBuffer selectQuery, List<Object> preparedStatementValues,
			AdvocateSearchCriteria advocateSearchCriteria) {

		int pageNumber = advocateSearchCriteria.getPageNumber();
		int pageSize = advocateSearchCriteria.getPageSize();
		int offset = 0;
		int limit = 0;

		limit = pageNumber * pageSize;

		if (pageNumber <= 1)
			offset = (limit - pageSize);
		else
			offset = (limit - pageSize) + 1;

		selectQuery.append(" offset ?  limit ?");
		preparedStatementValues.add(offset);
		preparedStatementValues.add(limit);
	}

	public static final String SEARCH_CASE_CODE_BY_ADVOCATE = "select casecode from egov_lcms_case_advocate where advocate->>'code'=?";

	/**
	 * This method is to build SELECT query to search Agencies
	 * 
	 * @param tenantId
	 * @param code
	 * @param tableName
	 * @param isIndividual
	 * @param preparedStatementValues
	 * @return String
	 */
	public static String getAgencyFieldsSearchQuery(String tenantId, String code, String tableName,
			Boolean isIndividual, List<Object> preparedStatementValues) {
		StringBuilder searchQuery = new StringBuilder();

		searchQuery.append("SELECT * FROM " + tableName);
		searchQuery.append(" WHERE tenantId =?");
		preparedStatementValues.add(tenantId);

		if (isIndividual) {
			searchQuery.append(" AND code =?");
			preparedStatementValues.add(code);
		} else {
			searchQuery.append(" AND agencycode =?");
			preparedStatementValues.add(code);
			searchQuery.append(" order by createdtime asc");
		}

		return searchQuery.toString();
	}

	/**
	 * This method is to build DELETE query to delete Advocates
	 * 
	 * @param code
	 * @param tenantId
	 * @param tableName
	 * @param preparedStatementValues
	 * @return String
	 */
	public static String getDeleteQuery(String code, String tenantId, String tableName,
			List<Object> preparedStatementValues) {
		StringBuilder deleteQuery = new StringBuilder();

		deleteQuery.append("DELETE FROM " + tableName);
		deleteQuery.append(" WHERE code =?");
		preparedStatementValues.add(code);

		if (tenantId != null) {
			deleteQuery.append(" AND tenantId =?");
			preparedStatementValues.add(tenantId);
		}

		return deleteQuery.toString();
	}

	/**
	 * This method is to build SELECT query to search Advocates
	 * 
	 * @param tenantId
	 * @param isIndividual
	 * @param advocateName
	 * @param agencyCode
	 * @param agencyName
	 * @param preparedStatementValues
	 * @return String
	 */
	public static String getAdvocateSearchQuery(String tenantId, Boolean isIndividual, String advocateName,
			String agencyCode, String agencyName, List<Object> preparedStatementValues) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(BASE_QUERY);
		selectQuery.append("WHERE tenantId=?");
		preparedStatementValues.add(tenantId);

		if (isIndividual != null) {
			selectQuery.append(" AND isindividual=?");
			preparedStatementValues.add(isIndividual);
		}

		if (agencyCode != null) {
			if (isIndividual) {
				selectQuery.append(" AND code=?");
				preparedStatementValues.add(agencyCode);
			} else {
				selectQuery.append(" AND agencyCode=?");
				preparedStatementValues.add(agencyCode);
			}
		}

		if (advocateName != null) {

			StringJoiner name = new StringJoiner("", "%", "%");
			name.add(advocateName);
			selectQuery.append(" AND LOWER(name) LIKE ? ");
			preparedStatementValues.add(name.toString().toLowerCase());
		}

		if (agencyName != null) {

			StringJoiner name = new StringJoiner("", "%", "%");
			name.add(agencyName);
			selectQuery.append(" AND LOWER(agencyname) LIKE ?");
			preparedStatementValues.add(name.toString().toLowerCase());
		}

		selectQuery.append(" ORDER BY lastmodifiedtime DESC");

		return selectQuery.toString();
	}

	/**
	 * This method is to build SELECT query to search Agencies
	 * 
	 * @param tenantId
	 * @param code
	 * @param isIndividual
	 * @param agencyName
	 * @param preparedStatementValues
	 * @return String
	 */
	public static String getAgencySearchQuery(String tenantId, String code, Boolean isIndividual, String agencyName,
			List<Object> preparedStatementValues) {

		StringBuilder searchQuery = new StringBuilder();
		searchQuery.append("SELECT * FROM " + ConstantUtility.AGENCY_TABLE_NAME);

		searchQuery.append(" WHERE tenantId=?");
		preparedStatementValues.add(tenantId);

		if (isIndividual != null) {
			searchQuery.append(" AND isIndividual=?");
			preparedStatementValues.add(isIndividual);
		}

		if (code != null) {
			searchQuery.append(" AND code=?");
			preparedStatementValues.add(code);
		}

		if (agencyName != null) {

			StringJoiner nameParam = new StringJoiner("", "%", "%");
			nameParam.add(agencyName);
			searchQuery.append(" AND LOWER(name) LIKE ?");
			preparedStatementValues.add(nameParam.toString().toLowerCase());
		}

		searchQuery.append(" ORDER BY lastmodifiedtime DESC");

		return searchQuery.toString();
	}

	/**
	 * This method is to build SELECT query to search agencies
	 * 
	 * @param codeList
	 * @param status
	 * @return String
	 */
	public static String getAgenciesWithAgencyCodeList(Set<String> codeList, String status,
			List<Object> preparedStatementValues) {
		StringBuilder searchQuery = new StringBuilder();
		searchQuery.append("SELECT * FROM " + ConstantUtility.AGENCY_TABLE_NAME);

		String[] codes = new String[codeList.size()];
		codes = codeList.toArray(codes);
		searchQuery.append(" WHERE code in(" + Stream.of(codes).collect(Collectors.joining("','", "'", "'")) + ")");

		if (status != null && !status.isEmpty()) {
			searchQuery.append(" And lower(status)=?");
			preparedStatementValues.add(status);
		}
		return searchQuery.toString();

	}
}
