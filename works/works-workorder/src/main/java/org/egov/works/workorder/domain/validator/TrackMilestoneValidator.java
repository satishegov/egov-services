package org.egov.works.workorder.domain.validator;

import org.egov.tracer.model.CustomException;
import org.egov.works.workorder.config.Constants;
import org.egov.works.workorder.domain.repository.MilestoneRepository;
import org.egov.works.workorder.web.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ramki on 26/12/17.
 */
@Service
public class TrackMilestoneValidator {
    @Autowired
    private MilestoneRepository milestoneRepository;

    public void validate(TrackMilestoneRequest trackMilestoneRequest, Boolean isUpdate) {
        Map<String, String> validationMessages = new HashMap<>();
        BigDecimal totalPercentage = BigDecimal.ZERO;
        for (TrackMilestone trackMilestone : trackMilestoneRequest.getTrackMilestones()) {
            if (trackMilestone.getMilestone().getId() != null && !trackMilestone.getMilestone().getId().isEmpty()) {
                if (milestoneRepository.search(MilestoneSearchContract.builder().tenantId(trackMilestone.getTenantId()).ids(Arrays.asList(trackMilestone.getMilestone().getId())).build(), trackMilestoneRequest.getRequestInfo()).isEmpty())
                    validationMessages.put(Constants.KEY_TRACKMILESTONE_MILESTONEID_INVALID, Constants.MESSAGE_TRACKMILESTONE_MILESTONEID_INVALID + trackMilestone.getMilestone().getId());
            } else {
                validationMessages.put(Constants.KEY_TRACKMILESTONE_MILESTONEID_IS_MANDATORY, Constants.MESSAGE_TRACKMILESTONE_MILESTONEID_IS_MANDATORY);
            }
            totalPercentage = validateTrackMilestoneActivities(trackMilestone.getTrackMilestoneActivities(), validationMessages, trackMilestoneRequest.getRequestInfo());
            if (trackMilestone.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_COMPLETED)) {
                if (totalPercentage.compareTo(new BigDecimal(100)) == -1)
                    validationMessages.put(Constants.KEY_TRACKMILESTONE_COMPLETED, Constants.MESSAGE_TRACKMILESTONE_COMPLETED);
            } else if (trackMilestone.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_NOT_YET_STARTED)) {
                if (totalPercentage.compareTo(BigDecimal.ZERO) == 1)
                    validationMessages.put(Constants.KEY_TRACKMILESTONE_NOT_STARTED, Constants.MESSAGE_TRACKMILESTONE_NOT_STARTED);
            } else if (trackMilestone.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_IN_PROGRESS)) {
                if (!(totalPercentage.compareTo(BigDecimal.ZERO) == 1 && totalPercentage.compareTo(new BigDecimal(100)) == -1))
                    validationMessages.put(Constants.KEY_TRACKMILESTONE_IN_PROGRESS, Constants.MESSAGE_TRACKMILESTONE_IN_PROGRESS);
            }
        }
        if (validationMessages.size() > 0) throw new CustomException(validationMessages);
    }

    private BigDecimal validateTrackMilestoneActivities(List<TrackMilestoneActivity> trackMilestoneActivities, Map<String, String> validationMessages, RequestInfo requestInfo) {
        Long currentDateInMillis = System.currentTimeMillis();
        MilestoneActivity milestoneActivity = null;
        BigDecimal totalPercentage = BigDecimal.ZERO;
        for (TrackMilestoneActivity trackMilestoneActivity : trackMilestoneActivities) {
            if (trackMilestoneActivity.getTenantId() == null || trackMilestoneActivity.getTenantId().isEmpty()) {
                validationMessages.put(Constants.KEY_TRACKMILESTONE_TENANTID_IS_MANDATORY, Constants.MESSAGE_TRACKMILESTONE_TENANTID_IS_MANDATORY);
            }
            if (trackMilestoneActivity.getMilestoneActivity().getId() == null || trackMilestoneActivity.getMilestoneActivity().getId().isEmpty()) {
                validationMessages.put(Constants.KEY_TRACKMILESTONE_MILESTONEACTIVITYID_IS_MANDATORY, Constants.MESSAGE_TRACKMILESTONE_MILESTONEACTIVITYID_IS_MANDATORY);
            } else {
                milestoneActivity = milestoneRepository.getActivityById(trackMilestoneActivity.getMilestoneActivity().getId(), trackMilestoneActivity.getTenantId());
                if (milestoneActivity == null)
                    validationMessages.put(Constants.KEY_TRACKMILESTONE_MILESTONEACTIVITYID_INVALID, Constants.MESSAGE_TRACKMILESTONE_MILESTONEACTIVITYID_INVALID + trackMilestoneActivity.getMilestoneActivity().getId());
                else {
                    if (trackMilestoneActivity.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_COMPLETED)) {
                        if (trackMilestoneActivity.getPercentage().compareTo(new BigDecimal(100)) == -1)
                            validationMessages.put(Constants.KEY_TRACKMILESTONE_COMPLETED, Constants.MESSAGE_TRACKMILESTONE_COMPLETED);
                    } else if (trackMilestoneActivity.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_NOT_YET_STARTED)) {
                        if (trackMilestoneActivity.getPercentage().compareTo(BigDecimal.ZERO) == 1)
                            validationMessages.put(Constants.KEY_TRACKMILESTONE_NOT_STARTED, Constants.MESSAGE_TRACKMILESTONE_NOT_STARTED);
                    } else if (trackMilestoneActivity.getStatus().getCode().equals(Constants.STATUS_TRACKMILESTONE_IN_PROGRESS)) {
                        if (!(trackMilestoneActivity.getPercentage().compareTo(BigDecimal.ZERO) == 1 && trackMilestoneActivity.getPercentage().compareTo(new BigDecimal(100)) == -1))
                            validationMessages.put(Constants.KEY_TRACKMILESTONE_IN_PROGRESS, Constants.MESSAGE_TRACKMILESTONE_IN_PROGRESS);
                    }
                    totalPercentage = totalPercentage.add(milestoneActivity.getPercentage().multiply(trackMilestoneActivity.getPercentage()).divide(new BigDecimal(100)));
                    if (trackMilestoneActivity.getActualStartDate() > currentDateInMillis || trackMilestoneActivity.getActualEndDate() > currentDateInMillis)
                        validationMessages.put(Constants.KEY_TRACKMILESTONE_ACTIVITY_ASD_AED_CANNOTBE_FUTURE, Constants.MESSAGE_TRACKMILESTONE_ACTIVITY_ASD_AED_CANNOTBE_FUTURE);
                    if (trackMilestoneActivity.getActualStartDate() > trackMilestoneActivity.getActualEndDate())
                        validationMessages.put(Constants.KEY_TRACKMILESTONE_ACTIVITY_AED_CANNOT_BEFORE_ASD, Constants.MESSAGE_TRACKMILESTONE_ACTIVITY_AED_CANNOT_BEFORE_ASD);
                }
            }

        }
        return totalPercentage;
    }
}
