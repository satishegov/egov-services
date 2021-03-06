package org.egov.swm.domain.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleMaintenance {

    @Size(min = 1, max = 256, message = "Value of code shall be between 1 and 128")
    @JsonProperty("code")
    private String code = null;

    @NotNull
    @Size(min = 1, max = 128, message = "Value of tenantId shall be between 1 and 128")
    @JsonProperty("tenantId")
    private String tenantId = null;

    @NotNull
    @JsonProperty("vehicle")
    private Vehicle vehicle = null;

    @NotNull
    @Size(min = 2, max = 5, message = "Value of maintenance uom shall be between 2 and 5")
    @JsonProperty("maintenanceUom")
    private String maintenanceUom = null;

    @NotNull
    @JsonProperty("maintenanceAfter")
    private Long maintenanceAfter = null;

    @NotNull
    @Size(min = 3, max = 5, message = "Value of downtimeforMaintenanceUom shall be between 3 and 5")
    @JsonProperty("downtimeforMaintenanceUom")
    private String downtimeforMaintenanceUom = null;

    @NotNull
    @JsonProperty("downtimeforMaintenance")
    private Double downtimeforMaintenance = null;

    @Valid
    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;
}
