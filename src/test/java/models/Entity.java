package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Основная модель сущности для тестирования API.
 * Представляет объект с основными атрибутами и дополнительными данными.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity {

    /**
     * Уникальный идентификатор сущности.
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * Заголовок сущности.
     */
    @JsonProperty("title")
    private String title;

    /**
     * Флаг верификации сущности.
     */
    @JsonProperty("verified")
    private Boolean verified;

    /**
     * Список важных чисел, связанных с сущностью.
     */
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    /**
     * Дополнительные данные сущности.
     */
    @JsonProperty("addition")
    private Addition addition;
}
