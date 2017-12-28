package org.egov.inv.domain.service;

import java.util.Calendar;
import java.util.List;

import org.egov.common.Constants;
import org.egov.common.exception.CustomBindException;
import org.egov.common.exception.ErrorCode;
import org.egov.common.exception.InvalidDataException;
import org.egov.inv.model.RequestInfo;
import org.egov.inv.model.Scrap;
import org.egov.inv.model.ScrapRequest;
import org.egov.inv.persistence.repository.ScrapJdbcRepository;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScrapService {
	
	@Autowired
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	private ScrapJdbcRepository scrapJdbcRepository;
	
	@Value("${inv.scrap.save.topic}")
	private String createTopic;
	
	@Value("${inv.scrap.update.topic}")
	private String updateTopic;
	
	public List<Scrap> create(ScrapRequest scrapReq, String tenantId) {
	try{
		validate(scrapReq.getScraps(), Constants.ACTION_CREATE,tenantId,scrapReq.getRequestInfo());
		kafkaTemplate.send(createTopic, scrapReq);
		return scrapReq.getScraps();
	}catch(CustomBindException e){
		throw e;
	}
	}
	
	public List<Scrap> update(ScrapRequest scrapReq, String tenantId) {
		try{
			validate(scrapReq.getScraps(), Constants.ACTION_UPDATE,tenantId,scrapReq.getRequestInfo());
			kafkaTemplate.send(updateTopic, scrapReq);
			return scrapReq.getScraps();
		}catch(CustomBindException e){
			throw e;
		}
		}
	
	private void validate(List<Scrap> receipt, String method, String tenantId,RequestInfo info) {
		InvalidDataException errors = new InvalidDataException();

		try {
			switch (method) {
				case Constants.ACTION_CREATE: {
					if (receipt == null) {
						errors.addDataError(ErrorCode.NOT_NULL.getCode(),"scrap", null);
					} 
				}
					break;
	
				case Constants.ACTION_UPDATE: {
					if (receipt == null) {
						errors.addDataError(ErrorCode.NOT_NULL.getCode(),"scrap", null);
					}
				}
					break;
	
				}
		}catch(IllegalArgumentException e){
			throw e;
		}
		if (errors.getValidationErrors().size() > 0)
			throw errors;
	}
	
	private String appendString(Scrap scrapReq) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String code = "SCRP/";
		int id = Integer.valueOf(scrapJdbcRepository.getSequence(scrapReq));
		String idgen = String.format("%05d", id);
		String scrapNumber = code + idgen + "/" + year;
		return scrapNumber;
	}
}


