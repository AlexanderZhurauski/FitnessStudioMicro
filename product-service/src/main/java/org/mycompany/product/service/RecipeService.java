package org.mycompany.product.service;

import jakarta.persistence.OptimisticLockException;
import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.recipe.RecipeCompositionCreateDTO;
import org.mycompany.product.core.dto.recipe.RecipeCreateDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.mycompany.product.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.product.dao.entities.Product;
import org.mycompany.product.dao.entities.ProductInstance;
import org.mycompany.product.dao.entities.Recipe;
import org.mycompany.product.dao.repositories.IRecipeRepository;
import org.mycompany.product.service.api.IProductService;
import org.mycompany.product.service.api.IRecipeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeService implements IRecipeService {

    private IRecipeRepository recipeRepository;
    private Converter<Recipe, RecipeDTO> toDTOConverter;
    private IProductService productService;

    public RecipeService(IRecipeRepository recipeRepository,
                         Converter<Recipe, RecipeDTO> toDTOConverter,
                         IProductService productService) {
        this.recipeRepository = recipeRepository;
        this.toDTOConverter = toDTOConverter;
        this.productService = productService;
    }

    @Override
    @Audited(operationType = OperationType.CREATE, entityType = EntityType.RECIPE)
    public UUID create(RecipeCreateDTO recipeCreateDTO) {
        Recipe recipe = convertToEntity(recipeCreateDTO);
        return  this.recipeRepository.save(recipe).getUuid();
    }

    @Override
    public Page<RecipeDTO> getPage(Pageable pageable) {
        Page<Recipe> recipePage = this.recipeRepository.findAll(pageable);
        return recipePage.map(this.toDTOConverter::convert);
    }

    @Override
    @Audited(operationType = OperationType.UPDATE, entityType = EntityType.RECIPE)
    public UUID update(UUID uuid, Instant lastUpdated, RecipeCreateDTO recipeCreateDTO) {
        Recipe recipe = this.recipeRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "recipe"));

        if (recipe.getLastUpdated().toEpochMilli() != lastUpdated.toEpochMilli()) {
            throw new OptimisticLockException("Recipe with id '" + recipe.getUuid()
                    + "' has already been modified!");
        }

        List<ProductInstance> productInstances = convertToProductInstances(recipeCreateDTO.getComposition());
        recipe.setTitle(recipeCreateDTO.getTitle());
        recipe.setComposition(productInstances);
        this.recipeRepository.save(recipe);

        return uuid;
    }

    private Recipe convertToEntity(RecipeCreateDTO recipeCreateDTO) {

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeCreateDTO.getTitle());

        List<RecipeCompositionCreateDTO> recipeComposition = recipeCreateDTO.getComposition();
        List<ProductInstance> productList = convertToProductInstances(recipeComposition);
        recipe.setComposition(productList);

        return recipe;
    }

    private List<ProductInstance> convertToProductInstances(
            List<RecipeCompositionCreateDTO> recipeCompositionCreateDTOS) {

        return recipeCompositionCreateDTOS.stream()
                .map(dto -> {
                    Product product = this.productService.getByID(dto.getProduct());
                    ProductInstance productInstance = new ProductInstance();

                    productInstance.setProduct(product);
                    productInstance.setWeight(dto.getWeight());

                    return productInstance;
                })
                .collect(Collectors.toList());
    }
}
