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
 * Представляет собой полную модель данных, используемую в системе для хранения
 * и передачи информации о бизнес-сущностях. Содержит как основные атрибуты,
 * так и дополнительные данные и мета-информацию.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity {

    /**
     * Уникальный идентификатор сущности в системе.
     * Используется для однозначной идентификации и ссылок на сущность.
     * Присваивается автоматически при создании новой записи.
     * Сериализуется в JSON как "id".
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * Заголовок или название сущности.
     * Основной текстовый атрибут, описывающий сущность.
     * Обязательное поле при создании новой сущности.
     * Сериализуется в JSON как "title".
     */
    @JsonProperty("title")
    private String title;

    /**
     * Флаг верификации сущности.
     * Указывает, прошла ли сущность проверку или moderation process.
     * Может использоваться для фильтрации или управления доступом.
     * Сериализуется в JSON как "verified".
     */
    @JsonProperty("verified")
    private Boolean verified;

    /**
     * Список важных чисел, ассоциированных с сущностью.
     * Может содержать различные числовые параметры, идентификаторы
     * или другие значимые числовые значения.
     * Сериализуется в JSON как "important_numbers".
     */
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    /**
     * Дополнительная информация о сущности.
     * Содержит расширенные данные, не входящие в основные атрибуты.
     * Опциональное поле - может отсутствовать для некоторых сущностей.
     * Сериализуется в JSON как "addition".
     */
    @JsonProperty("addition")
    private Addition addition;
}
