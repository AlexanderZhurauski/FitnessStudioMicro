package org.mycompany.product.service.api;

import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.dao.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IProductService {
    UUID create(ProductCreateDTO productCreateDTO);
    Page<ProductDTO> getPage(Pageable pageable);
    Product getByID(UUID uuid);
    UUID update(UUID uuid, Instant lastUpdated, ProductCreateDTO productCreateDTO);
}
