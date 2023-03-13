package org.mycompany.product.core.dto.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductCreateDTO {
    @NotBlank(message = "The title cannot be blank!")
    @NotNull(message = "No title has been provided!")
    private String title;
    @Positive(message = "The weight value must be positive!")
    private int weight;
    @Positive(message = "The calorie value must be positive!")
    private int calories;
    @Positive(message = "The protein value must be positive!")
    private double proteins;
    @Positive(message = "The fat value must be positive!")
    private double fats;
    @Positive(message = "The carbohydrate value must be positive!")
    private double carbohydrates;

    public ProductCreateDTO() {
    }

    public ProductCreateDTO(String title, int weight, int calories,
                            double proteins, double fats,
                            double carbohydrates) {
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
