package org.mycompany.product.dao.repositories;

import org.mycompany.product.dao.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IProductRepository extends CrudRepository<Product, UUID>,
        PagingAndSortingRepository<Product, UUID> {
}
