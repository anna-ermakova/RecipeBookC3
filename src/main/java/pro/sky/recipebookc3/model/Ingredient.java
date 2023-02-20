package pro.sky.recipebookc3.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Ingredient {
    @NotBlank(message = "Обязательно для заполнения!")
    private String ingredientName;
    @Positive
    private Integer volume;
    private String unitOfMeasure;
}


