package org.mycompany.product.converters.domain;

import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.dao.entities.Product;
import org.springframework.core.convert.converter.Converter;

public class ProductToEntityConverter implements Converter<ProductCreateDTO, Product> {
    @Override
    public Product convert(ProductCreateDTO productCreateDTO) {

        Product product = new Product();
        product.setTitle(productCreateDTO.getTitle());
        product.setWeight(productCreateDTO.getWeight());
        product.setCalories(productCreateDTO.getCalories());
        product.setProteins(productCreateDTO.getProteins());
        product.setFats(productCreateDTO.getFats());
        product.setCarbohydrates(productCreateDTO.getCarbohydrates());

        return product;
    }
}
