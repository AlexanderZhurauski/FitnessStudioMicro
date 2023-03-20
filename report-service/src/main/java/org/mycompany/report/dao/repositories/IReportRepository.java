package org.mycompany.report.dao.repositories;

import org.mycompany.report.dao.entities.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IReportRepository extends PagingAndSortingRepository<Report, UUID>,
        CrudRepository<Report, UUID> {
}
