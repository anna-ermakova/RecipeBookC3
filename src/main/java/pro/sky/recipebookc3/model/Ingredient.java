package pro.sky.recipebookc3.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Ingredient {
    @NotBlank(message = "Обязательно для заполнения!")
    private String ingredientName;
    @Positive
    private Integer volume;
    private String unitOfMeasure;
}


