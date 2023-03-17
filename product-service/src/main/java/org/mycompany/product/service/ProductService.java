package org.mycompany.product.service;

import jakarta.persistence.OptimisticLockException;
import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.product.dao.entities.Product;
import org.mycompany.product.dao.repositories.IProductRepository;
import org.mycompany.product.service.api.IProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public class ProductService implements IProductService {

    private IProductRepository productRepository;
    private Converter<ProductCreateDTO, Product> toEntityConverter;
    private Converter<Product, ProductDTO> toDTOConverter;

    public ProductService(IProductRepository productRepository,
                          Converter<ProductCreateDTO, Product> toEntityConverter,
                          Converter<Product, ProductDTO> toDTOConverter) {
        this.productRepository = productRepository;
        this.toEntityConverter = toEntityConverter;
        this.toDTOConverter = toDTOConverter;
    }

    @Override
    @Audited(operationType = OperationType.CREATE, entityType = EntityType.PRODUCT)
    public UUID create(ProductCreateDTO productCreateDTO) {
        Product product = toEntityConverter.convert(productCreateDTO);
        return  this.productRepository.save(product).getUuid();
    }

    @Override
    public Page<ProductDTO> getPage(Pageable pageable) {
        Page<Product> productPage = this.productRepository.findAll(pageable);
        return productPage.map(toDTOConverter::convert);
    }

    @Override
    public Product getByID(UUID uuid) {
        return this.productRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "product"));
    }

    @Override
    @Audited(operationType = OperationType.UPDATE, entityType = EntityType.PRODUCT)
    public UUID update(UUID uuid, Instant lastUpdated,
                             ProductCreateDTO productCreateDTO) {

        Product product = this.productRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "product"));

        if (product.getLastUpdated().toEpochMilli() != lastUpdated.toEpochMilli()) {
            throw new OptimisticLockException("Product with id '" + product.getUuid()
                    + "' has already been modified!");
        }

        product.setTitle(productCreateDTO.getTitle());
        product.setCarbohydrates(productCreateDTO.getCarbohydrates());
        product.setFats(productCreateDTO.getFats());
        product.setProteins(productCreateDTO.getProteins());
        product.setCalories(productCreateDTO.getCalories());
        product.setWeight(productCreateDTO.getWeight());
        this.productRepository.save(product);

        return uuid;
    }
}
