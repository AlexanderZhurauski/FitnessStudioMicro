package org.mycompany.product.config;

import org.mycompany.product.converters.domain.*;
import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.mycompany.product.dao.entities.Product;
import org.mycompany.product.dao.entities.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterConfig {
    @Bean
    public Converter<Product, ProductDTO> productToDTOConverter() {
        return new ProductToDTOConverter();
    }

    @Bean
    public Converter<ProductCreateDTO, Product> productToEntityConverter() {
        return new ProductToEntityConverter();
    }

    @Bean
    public Converter<Recipe, RecipeDTO> recipeToDTOConverter() {
        return new RecipeToDTOConverter(productToDTOConverter());
    }

}
