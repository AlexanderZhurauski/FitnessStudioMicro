package org.mycompany.product.core.dto.product;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.mycompany.product.core.dto.BaseEssence;

public class ProductDTO {
    @JsonUnwrapped
    private BaseEssence baseEssence;
    private String title;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public ProductDTO() {
    }

    public ProductDTO(BaseEssence baseEssence, String title, int weight,
                      int calories, double proteins,
                      double fats, double carbohydrates) {
        this.baseEssence = baseEssence;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public BaseEssence getBaseEssence() {
        return this.baseEssence;
    }

    public void setBaseEssence(BaseEssence baseEssence) {
        this.baseEssence = baseEssence;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return this.proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return this.fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
