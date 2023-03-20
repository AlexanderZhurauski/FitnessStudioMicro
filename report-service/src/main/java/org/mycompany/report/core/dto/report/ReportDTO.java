package org.mycompany.report.core.dto.report;

import jakarta.validation.constraints.NotNull;
import org.mycompany.report.core.validation.DateRange;

import java.time.LocalDate;
import java.util.UUID;

@DateRange(from = "from", to = "to")
public class ReportDTO {
    @NotNull(message = "The report request must have a 'UUID' field")
    private UUID user;
    @NotNull(message = "The report request must have a 'from' field")
    private LocalDate from;
    @NotNull(message = "The report request must have a 'to' field")
    private LocalDate to;

    public ReportDTO() {
    }

    public ReportDTO(UUID user, LocalDate from, LocalDate to) {
        this.user = user;
        this.from = from;
        this.to = to;
    }

    public UUID getUser() {
        return this.user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
