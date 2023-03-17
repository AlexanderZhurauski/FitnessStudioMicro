package org.mycompany.product.service.api;

import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.recipe.RecipeCreateDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IRecipeService {

    @Audited(operationType = OperationType.CREATE, entityType = EntityType.RECIPE)
    void create(RecipeCreateDTO recipeCreateDTO);
    Page<RecipeDTO> getPage(Pageable pageable);
    @Audited(operationType = OperationType.UPDATE, entityType = EntityType.RECIPE)
    void update(UUID uuid, Instant lastUpdated, RecipeCreateDTO recipeCreateDTO);
}
