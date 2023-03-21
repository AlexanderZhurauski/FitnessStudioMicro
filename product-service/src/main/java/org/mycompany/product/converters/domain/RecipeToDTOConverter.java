package org.mycompany.product.converters.domain;

import org.mycompany.product.core.dto.BaseEssence;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.core.dto.recipe.RecipeCompositionDTO;
import org.mycompany.product.core.dto.recipe.RecipeDTO;
import org.mycompany.product.dao.entities.Product;
import org.mycompany.product.dao.entities.ProductInstance;
import org.mycompany.product.dao.entities.Recipe;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class RecipeToDTOConverter implements Converter<Recipe, RecipeDTO> {

    private Converter<Product, ProductDTO> productToDTOConverter;

    public RecipeToDTOConverter(Converter<Product, ProductDTO> productToDTOConverter) {
        this.productToDTOConverter = productToDTOConverter;
    }
    @Override
    public RecipeDTO convert(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        BaseEssence baseEssence = new BaseEssence(recipe.getUuid(),
                recipe.getCreationTime(), recipe.getLastUpdated());
        recipeDTO.setBaseEssence(baseEssence);
        recipeDTO.setTitle(recipe.getTitle());

        List<ProductInstance> productList = recipe.getComposition();
        List<RecipeCompositionDTO> productComposition = calculateRecipeComposition(productList);
        recipeDTO.setComposition(productComposition);

        int weight = (int) round(productComposition
                .stream()
                .mapToInt(RecipeCompositionDTO::getWeight)
                .sum(), 2);
        int calories = (int) round(productComposition
                .stream()
                .mapToInt(RecipeCompositionDTO::getCalories)
                .sum(), 2);
        double proteins = round(productComposition
                .stream()
                .mapToDouble(RecipeCompositionDTO::getProteins)
                .sum(), 2);
        double fats = round(productComposition
                .stream()
                .mapToDouble(RecipeCompositionDTO::getFats)
                .sum(), 2);
        double carbohydrates = round(productComposition
                .stream()
                .mapToDouble(RecipeCompositionDTO::getCarbohydrates)
                .sum(), 2);

        recipeDTO.setWeight(weight);
        recipeDTO.setCalories(calories);
        recipeDTO.setProteins(round(proteins, 1));
        recipeDTO.setFats(round(fats, 1));
        recipeDTO.setCarbohydrates(round(carbohydrates, 1));

        return  recipeDTO;
    }

    private List<RecipeCompositionDTO> calculateRecipeComposition(List<ProductInstance> productList) {
        List<RecipeCompositionDTO> recipeComposition = productList.stream()
                .map(product -> {
                    RecipeCompositionDTO compositionDTO = new RecipeCompositionDTO();

                    ProductDTO productDTO = this.productToDTOConverter.convert(product.getProduct());
                    int actualWeight = product.getWeight();
                    int standardWeight = productDTO.getWeight();
                    double ratio = 1.0 * actualWeight / standardWeight;
                    int calories = (int) (ratio * productDTO.getCalories());
                    double proteins = round(ratio * productDTO.getProteins(),1);
                    double fats = round(ratio * productDTO.getFats(), 1);
                    double carbohydrates = round(ratio * productDTO.getCarbohydrates(), 1);

                    compositionDTO.setProduct(productDTO);
                    compositionDTO.setWeight(actualWeight);
                    compositionDTO.setCalories(calories);
                    compositionDTO.setProteins(proteins);
                    compositionDTO.setFats(fats);
                    compositionDTO.setCarbohydrates(carbohydrates);

                    return compositionDTO;
                })
                .toList();

        return recipeComposition;
    }

    private double round(double number, int places) {
        if (places < 1 || places > 1000) {
            throw new IllegalArgumentException("Can't round to '"
                    + places + "' decimal places!");
        }
        double multiplier = 10.0 * places;
        return Math.round(number * multiplier) / multiplier;
    }
}
