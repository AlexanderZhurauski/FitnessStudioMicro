package org.mycompany.product.config;

import org.mycompany.product.audit.advice.AuditAspect;
import org.mycompany.product.core.dto.audit.AuditDTO;
import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.mycompany.product.dao.entities.*;
import org.mycompany.product.dao.repositories.*;
import org.mycompany.product.security.UserHolder;
import org.mycompany.product.service.*;
import org.mycompany.product.service.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.kafka.core.KafkaTemplate;


@Configuration
public class ServiceConfig {

    @Bean
    public IProductService productService(IProductRepository productRepository,
                                          Converter<ProductCreateDTO, Product> toEntityConverter,
                                          Converter<Product, ProductDTO> toDTOConverter) {

        return new ProductService(productRepository, toEntityConverter, toDTOConverter);
    }

    @Bean
    public IRecipeService recipeService(IRecipeRepository recipeRepository,
                                        Converter<Recipe, RecipeDTO> toDTOConverter,
                                        IProductService productService) {

        return new RecipeService(recipeRepository, toDTOConverter, productService);
    }

    @Bean
    public AuditAspect auditAspect(UserHolder userHolder, KafkaTemplate<String, AuditDTO> kafkaTemplate) {
        return new AuditAspect(userHolder, kafkaTemplate);
    }
}
