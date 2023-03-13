package org.mycompany.product.core.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class RecipeCreateDTO {

    @NotBlank(message = "The title cannot be blank!")
    @NotNull(message = "No title has been provided!")
    private String title;
    @Size(min = 1, message = "A recipe must contain at least one ingredient!")
    @NotNull(message = "No recipe composition has been provided!")
    private List<RecipeCompositionCreateDTO> composition;

    public RecipeCreateDTO() {
    }

    public RecipeCreateDTO(String title,
                           List<RecipeCompositionCreateDTO> composition) {
        this.title = title;
        this.composition = composition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RecipeCompositionCreateDTO> getComposition() {
        return this.composition != null ? this.composition : new ArrayList<>();
    }

    public void setComposition(List<RecipeCompositionCreateDTO> composition) {
        this.composition = composition;
    }

    public void addRecipe(RecipeCompositionCreateDTO recipe) {
        if (this.composition == null) {
            this.composition = new ArrayList<>();
        }
        this.composition.add(recipe);
    }

    public void deleteRecipe(RecipeCompositionCreateDTO recipe) {
        if (this.composition == null) {
            this.composition = new ArrayList<>();
            return;
        }
        this.composition.remove(recipe);
    }
}
