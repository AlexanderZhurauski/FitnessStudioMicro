package org.mycompany.product.core.dto.recipe;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.mycompany.product.core.dto.BaseEssence;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {

    @JsonUnwrapped
    private BaseEssence baseEssence;
    private String title;
    private List<RecipeCompositionDTO> composition;

    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
    public RecipeDTO() {
    }

    public RecipeDTO(BaseEssence baseEssence, String title,
                     List<RecipeCompositionDTO> composition, int weight,
                     int calories, double proteins,
                     double fats, double carbohydrates) {
        this.baseEssence = baseEssence;
        this.title = title;
        this.composition = composition;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public BaseEssence getBaseEssence() {
        return baseEssence;
    }

    public void setBaseEssence(BaseEssence baseEssence) {
        this.baseEssence = baseEssence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RecipeCompositionDTO> getComposition() {
        return composition;
    }

    public void setComposition(List<RecipeCompositionDTO> composition) {
        this.composition = composition;
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

    public void addRecipe(RecipeCompositionDTO recipe) {
        if (this.composition == null) {
            this.composition = new ArrayList<>();
        }
        this.composition.add(recipe);
    }

    public void deleteRecipe(RecipeCompositionDTO recipe) {
        if (this.composition == null) {
            this.composition = new ArrayList<>();
            return;
        }
        this.composition.remove(recipe);
    }
}
