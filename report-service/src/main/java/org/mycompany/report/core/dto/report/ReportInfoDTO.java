package org.mycompany.report.core.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mycompany.report.converters.json.InstantToUnix;
import org.mycompany.report.converters.json.UnixToInstant;
import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.enums.ReportType;

import java.time.Instant;
import java.util.UUID;

public class ReportInfoDTO {

    private UUID uuid;
    @JsonSerialize(converter = InstantToUnix.class)
    @JsonDeserialize(converter = UnixToInstant.class)
    private Instant creationTime;
    @JsonSerialize(converter = InstantToUnix.class)
    @JsonDeserialize(converter = UnixToInstant.class)
    private Instant lastUpdated;
    ReportStatus status;
    ReportType type;
    private String description;
    private ReportDTO params;

    public ReportInfoDTO() {
    }

    public ReportInfoDTO(UUID uuid, Instant creationTime, Instant lastUpdated,
                         ReportStatus status, ReportType type, String description,
                         ReportDTO params) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.lastUpdated = lastUpdated;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    @JsonProperty("dt_create")
    public Instant getCreationTime() {
        return this.creationTime;
    }
    @JsonProperty("dt_create")
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
    @JsonProperty("dt_update")
    public Instant getLastUpdated() {
        return this.lastUpdated;
    }
    @JsonProperty("dt_update")
    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ReportStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportType getType() {
        return this.type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportDTO getParams() {
        return this.params;
    }

    public void setParams(ReportDTO params) {
        this.params = params;
    }
}
