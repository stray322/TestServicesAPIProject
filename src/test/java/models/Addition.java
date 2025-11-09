package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель дополнительных данных сущности.
 * Содержит дополнительную информацию о сущности, такую как произвольный текст и числовое значение.
 * Используется для расширения основной информации сущности.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Addition {

    /**
     * Дополнительная текстовая информация о сущности.
     * Может содержать произвольные сведения, расширяющие основное описание.
     * Сериализуется в JSON как "additional_info".
     */
    @JsonProperty("additional_info")
    private String additionalInfo;

    /**
     * Дополнительное числовое значение, связанное с сущностью.
     * Может использоваться для хранения различных числовых параметров
     * или идентификаторов связанных объектов.
     * Сериализуется в JSON как "additional_number".
     */
    @JsonProperty("additional_number")
    private Integer additionalNumber;

    /**
     * Уникальный идентификатор дополнительной информации.
     * Используется для однозначной идентизации записи в системе.
     * Обычно присваивается автоматически при создании сущности.
     * Сериализуется в JSON как "id".
     */
    @JsonProperty("id")
    private Integer id;
}
