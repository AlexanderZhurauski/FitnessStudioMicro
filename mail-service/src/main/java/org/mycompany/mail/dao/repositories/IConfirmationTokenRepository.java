package org.mycompany.mail.dao.repositories;

import org.mycompany.mail.dao.entities.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface IConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
}
