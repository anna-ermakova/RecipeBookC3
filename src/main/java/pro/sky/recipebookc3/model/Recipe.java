package pro.sky.recipebookc3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Recipe {
    @NotBlank(message = "Обязательно для заполнения!")
    private String recipeName;
    @Positive
    private int cookingTimeMin;
    @NotEmpty
    private List<Ingredient> ingredients;
    @NotEmpty
    private List<String> cookingSteps;

}
