package org.mycompany.report.dao.entities;

import jakarta.persistence.*;
import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.enums.ReportType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "report", schema = "app")
public class Report {
    @Id
    @GeneratedValue
    private UUID uuid;
    @Column(name = "creation_time")
    private Instant creationTime;
    @Column(name = "last_updated")
    @Version
    private Instant lastUpdated;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ReportType type;
    private String description;
    @Column(name = "user_id")
    private UUID userID;
    @Column(name = "start_time")
    private Instant from;
    @Column(name = "end_time")
    private Instant to;

    public Report() {
    }

    public Report(UUID uuid, Instant creationTime, Instant lastUpdated,
                  ReportStatus status, ReportType type, String description,
                  UUID userID, Instant from, Instant to) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.lastUpdated = lastUpdated;
        this.status = status;
        this.type = type;
        this.description = description;
        this.userID = userID;
        this.from = from;
        this.to = to;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

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

    public UUID getUserID() {
        return this.userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public Instant getFrom() {
        return this.from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return this.to;
    }

    public void setTo(Instant to) {
        this.to = to;
    }
}
