package org.egov.demand.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.demand.config.ApplicationProperties;
import org.egov.demand.model.AuditDetail;
import org.egov.demand.model.Demand;
import org.egov.demand.model.DemandCriteria;
import org.egov.demand.model.DemandDetail;
import org.egov.demand.model.DemandDetailCriteria;
import org.egov.demand.repository.DemandRepository;
import org.egov.demand.web.contract.DemandRequest;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DemandService {
	
	@Autowired
	private SequenceGenService sequenceGenService;
	
	@Autowired
	private DemandRepository demandRepository;
	
	@Autowired
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	private ApplicationProperties applicationProperties;

	public List<Demand> create(DemandRequest demandRequest) {
		
		log.info("the demand service : "+demandRequest);
		List<Demand> demands = demandRequest.getDemands();
		List<DemandDetail> demandDetails = new ArrayList<>();
		AuditDetail auditDetail = getAuditDetail(demandRequest.getRequestInfo());
		
		int currentDemandId = 0;
		int demandsSize = demands.size();
		List<String> demandIds = sequenceGenService.getIds(demandsSize,applicationProperties.getDemandSeqName());
		
		for (Demand demand : demands) {
			String demandId = demandIds.get(currentDemandId++);
			demand.setId(demandId);
			demand.setAuditDetail(auditDetail);
			String tenantId = demand.getTenantId();
			for (DemandDetail demandDetail : demand.getDemandDetails()) {
				demandDetail.setDemandId(demandId);
				demandDetail.setTenantId(tenantId);
				demandDetail.setAuditDetail(auditDetail);
				demandDetails.add(demandDetail);
			}
		}
		
		int demandDetailsSize = demandDetails.size();
		List<String> demandDetailIds = sequenceGenService.getIds(demandDetailsSize,applicationProperties.getDemandDetailSeqName());
		int currentDetailId = 0;
		for (DemandDetail demandDetail : demandDetails) {
			demandDetail.setId(demandDetailIds.get(currentDetailId++));
		}
		kafkaTemplate.send(applicationProperties.getCreateDemandTopic(), demandRequest);
		log.info("demand Request object : "+demandRequest);
		log.info("demand detail list : "+demandDetails);
		return demands;
	}
	
	private AuditDetail getAuditDetail(RequestInfo requestInfo) {

		String userId = requestInfo.getUserInfo().getId().toString();
		Long currEpochDate = new Date().getTime();
		
		AuditDetail auditDetail = new AuditDetail();
		auditDetail.setCreatedBy(userId);
		auditDetail.setCreatedTime(currEpochDate);
		auditDetail.setLastModifiedBy(userId);
		auditDetail.setLastModifiedTime(currEpochDate);
		return auditDetail;
	}

	public List<Demand> updateAsync(DemandRequest demandRequest) {

		log.info("the demand service : " + demandRequest);
		List<Demand> demands = demandRequest.getDemands();
		String userId = demandRequest.getRequestInfo().getUserInfo().getId().toString();
		Long currEpochDate = new Date().getTime();
		
		for (Demand demand : demands) {
			AuditDetail auditDetail = demand.getAuditDetail();
			auditDetail.setLastModifiedBy(userId);
			auditDetail.setLastModifiedTime(currEpochDate);
			for (DemandDetail demandDetail : demand.getDemandDetails()) {
				AuditDetail auditDetail1 = demandDetail.getAuditDetail();
				auditDetail1.setLastModifiedBy(userId);
				auditDetail1.setLastModifiedTime(currEpochDate);
			}
		}
		kafkaTemplate.send(applicationProperties.getCreateDemandTopic(), demandRequest);
		return demandRequest.getDemands();
	}
	
	public List<Demand> getDemands(DemandCriteria demandCriteria){
		return demandRepository.getDemands(demandCriteria);
	}
	
	public List<DemandDetail> getDemandDetails(DemandDetailCriteria demandDetailCriteria){
		return demandRepository.getDemandDetails(demandDetailCriteria);
	}

	public void save(DemandRequest demandRequest) {
		demandRepository.save(demandRequest);
	}
	
	public void update(DemandRequest demandRequest) {
		demandRepository.update(demandRequest);
	}
}
