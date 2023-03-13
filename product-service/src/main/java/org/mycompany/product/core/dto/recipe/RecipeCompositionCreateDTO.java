package org.mycompany.product.core.dto.recipe;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;
import java.util.UUID;

public class RecipeCompositionCreateDTO {
    @NotNull(message = "A recipe ingredient must have a valid product UUID!")
    private UUID product;
    @Positive(message = "The weight value must be positive!")
    private int weight;

    public RecipeCompositionCreateDTO() {
    }

    public RecipeCompositionCreateDTO(UUID product, int weight) {
        this.product = product;
        this.weight = weight;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCompositionCreateDTO that = (RecipeCompositionCreateDTO) o;
        return weight == that.weight && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, weight);
    }
}
