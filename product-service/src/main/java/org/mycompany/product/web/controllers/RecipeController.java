package org.mycompany.product.web.controllers;

import org.mycompany.product.core.dto.recipe.RecipeCreateDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.mycompany.product.service.api.IRecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    private IRecipeService recipeService;

    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody RecipeCreateDTO recipe) {
        this.recipeService.create(recipe);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public Page<RecipeDTO> getUserPage(Pageable pageable) {
        return this.recipeService.getPage(pageable);
    }

    @PutMapping("/{uuid}/dt_update/{lastUpdated}")
    public ResponseEntity<String> updateUser(@PathVariable UUID uuid,
                                                 @PathVariable Instant lastUpdated,
                                                 @RequestBody RecipeCreateDTO product) {

        this.recipeService.update(uuid, lastUpdated, product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
