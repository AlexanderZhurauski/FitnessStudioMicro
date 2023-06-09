package org.mycompany.product.core.dto.recipe;

import org.mycompany.product.core.dto.product.ProductDTO;

import java.util.Objects;

public class RecipeCompositionDTO {

    private ProductDTO product;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public RecipeCompositionDTO() {
    }

    public RecipeCompositionDTO(ProductDTO product, int weight,
                                int calories, double proteins,
                                double fats, double carbohydrates) {

        this.product = product;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCompositionDTO that = (RecipeCompositionDTO) o;
        return weight == that.weight && calories == that.calories
                && Double.compare(that.proteins, proteins) == 0
                && Double.compare(that.fats, fats) == 0
                && Double.compare(that.carbohydrates, carbohydrates) == 0
                && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, weight, calories,
                proteins, fats, carbohydrates);
    }
}
