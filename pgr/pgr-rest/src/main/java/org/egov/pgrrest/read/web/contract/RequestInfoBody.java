package org.egov.pgrrest.read.web.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.pgrrest.common.model.AuthenticatedUser;
import org.egov.pgrrest.common.model.UserType;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RequestInfoBody {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    public boolean isAnonymous() {
        return this.getRequestInfo() == null || this.getRequestInfo().getUserInfo() == null;
    }

    @JsonIgnore
    public AuthenticatedUser toAuthenticatedUser() {
        User userInfo = getUserInfo();
        if (userInfo == null) {
            return AuthenticatedUser.createAnonymousUser();
        }
        return AuthenticatedUser.builder()
            .roleCodes(getRoles(userInfo.getRoles()))
            .type(UserType.valueOf(userInfo.getType()))
            .email(userInfo.getEmailId())
            .mobileNumber(userInfo.getMobileNumber())
            .name(userInfo.getName())
            .id(userInfo.getId())
            .build();
    }

    @JsonIgnore
    private User getUserInfo() {
        return requestInfo.getUserInfo();
    }

    private List<String> getRoles(List<org.egov.common.contract.request.Role> roles) {
        return roles.stream().map(role -> role.getCode()).collect(Collectors.toList());
    }
}