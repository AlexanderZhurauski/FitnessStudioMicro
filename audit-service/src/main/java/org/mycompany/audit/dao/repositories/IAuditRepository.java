package org.mycompany.audit.dao.repositories;

import org.mycompany.audit.dao.entities.AuditEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IAuditRepository extends PagingAndSortingRepository<AuditEntity, UUID>,
        CrudRepository<AuditEntity, UUID> {
}
