package org.egov.tradelicense.web.repository;

import org.egov.models.PropertyResponse;
import org.egov.tradelicense.common.config.PropertiesManager;
import org.egov.tl.commons.web.requests.RequestInfoWrapper;
import org.egov.tradelicense.domain.model.TradeLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PropertyContractRespository {

	private RestTemplate restTemplate;
	private String hostUrl;
	private String searchUrl ;
	
	@Autowired
	private PropertiesManager propertiesManger;

	public PropertyContractRespository(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.hostUrl = propertiesManger.getPropertyHostname() + propertiesManger.getPropertyBasepath();
		this.searchUrl = propertiesManger.getPropertySearchpath();
	}
	public PropertyResponse findByAssesmentNo(TradeLicense tradeLicense, RequestInfoWrapper requestInfoWrapper) {

		String url = String.format("%s%s", hostUrl, searchUrl);
		StringBuffer content = new StringBuffer();
		if (tradeLicense.getLocalityId() != null) {
			content.append("upicNumber=" + tradeLicense.getPropertyAssesmentNo());
		}

		if (tradeLicense.getTenantId() != null) {
			content.append("&tenantId=" + tradeLicense.getTenantId());
		}
		url = url + content.toString();
		PropertyResponse propertyResponse = null;
		try {

			propertyResponse = restTemplate.postForObject(url, requestInfoWrapper, PropertyResponse.class);

		} catch (Exception e) {
			log.error("Error while connecting to the proeprty end point");
		}

		if (propertyResponse != null && propertyResponse.getProperties() != null
				&& propertyResponse.getProperties().size() > 0) {
			return propertyResponse;
		} else {
			return null;
		}

	}
}
