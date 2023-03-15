package org.mycompany.audit.dao.repositories;

import org.mycompany.audit.dao.entities.AuditEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IAuditRepository extends CrudRepository<AuditEntity, UUID> {
}
