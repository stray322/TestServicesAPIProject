package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель дополнительных данных сущности.
 * Содержит дополнительную информацию и числовое значение.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addition {

    /**
     * Дополнительная текстовая информация.
     */
    @JsonProperty("additional_info")
    private String additionalInfo;

    /**
     * Дополнительное числовое значение.
     */
    @JsonProperty("additional_number")
    private Integer additionalNumber;

    /**
     * Уникальный идентификатор дополнительных данных.
     */
    @JsonProperty("id")
    private Integer id;
}
