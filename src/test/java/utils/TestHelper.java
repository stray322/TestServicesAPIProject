package utils;

import io.qameta.allure.Allure;
import io.restassured.specification.RequestSpecification;
import models.Entity;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Вспомогательный класс для общих операций с сущностями в тестах.
 * Содержит методы для создания, получения и удаления сущностей.
 */
public class TestHelper {

    /**
     * Создает сущность через API и возвращает её идентификатор.
     *
     * @param entity сущность для создания
     * @param requestSpec спецификация REST запроса
     * @return идентификатор созданной сущности
     * @throws AssertionError если создание сущности завершилось ошибкой
     */
    public static Integer createEntityAndGetId(Entity entity, RequestSpecification requestSpec) {
        try {
            String response = given()
                    .spec(requestSpec)
                    .body(entity)
                    .when()
                    .post(APIEndpoints.CREATE_ENDPOINT)
                    .then()
                    .statusCode(200)
                    .body(notNullValue())
                    .extract()
                    .body()
                    .asString();

            Integer entityId = Integer.parseInt(response);
            Allure.addAttachment("Создана сущность", "text/plain",
                    String.format("ID: %d, Title: %s", entityId, entity.getTitle()));
            return entityId;

        } catch (Exception e) {
            String errorMessage = String.format("Не удалось создать сущность: %s", e.getMessage());
            Allure.addAttachment("Ошибка создания сущности", "text/plain", errorMessage);
            throw new AssertionError(errorMessage, e);
        }
    }

    /**
     * Удаляет сущность по идентификатору.
     *
     * @param entityId идентификатор сущности для удаления
     * @param requestSpec спецификация REST запроса
     * @throws AssertionError если удаление сущности завершилось ошибкой
     */
    public static void deleteEntity(Integer entityId, RequestSpecification requestSpec) {
        try {
            given()
                    .spec(requestSpec)
                    .pathParam("id", entityId)
                    .delete(APIEndpoints.DELETE_ENDPOINT)
                    .then()
                    .statusCode(204);

            Allure.addAttachment("Удалена сущность", "text/plain",
                    String.format("ID: %d", entityId));

        } catch (Exception e) {
            String errorMessage = String.format("Не удалось удалить сущность с ID %d: %s", entityId, e.getMessage());
            Allure.addAttachment("Ошибка удаления сущности", "text/plain", errorMessage);
            throw new AssertionError(errorMessage, e);
        }
    }

    /**
     * Получает сущность по идентификатору и десериализует ответ.
     *
     * @param entityId идентификатор сущности
     * @param requestSpec спецификация REST запроса
     * @return десериализованный объект сущности
     * @throws AssertionError если получение сущности завершилось ошибкой
     */
    public static Entity getEntityById(Integer entityId, RequestSpecification requestSpec) {
        try {
            Entity entity = given()
                    .spec(requestSpec)
                    .pathParam("id", entityId)
                    .when()
                    .get(APIEndpoints.GET_ENDPOINT)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Entity.class);

            Allure.addAttachment("Получена сущность", "application/json",
                    String.format("ID: %d, Title: %s", entityId, entity.getTitle()));
            return entity;

        } catch (Exception e) {
            String errorMessage = String.format("Не удалось получить сущность с ID %d: %s", entityId, e.getMessage());
            Allure.addAttachment("Ошибка получения сущности", "text/plain", errorMessage);
            throw new AssertionError(errorMessage, e);
        }
    }

    /**
     * Безопасно извлекает список сущностей из ответа, анализируя его структуру.
     *
     * @param response объект ответа от API
     * @return список сущностей или null, если список не найден
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> safelyExtractEntitiesList(Object response) {
        if (response instanceof List) {
            return (List<Map<String, Object>>) response;
        } else if (response instanceof Map) {
            Map<String, Object> responseMap = (Map<String, Object>) response;

            String[] possibleListFields = {"content", "data", "items", "entities", "results", "list"};
            for (String field : possibleListFields) {
                if (responseMap.containsKey(field) && responseMap.get(field) instanceof List) {
                    return (List<Map<String, Object>>) responseMap.get(field);
                }
            }

            for (Object value : responseMap.values()) {
                if (value instanceof List) {
                    List<?> list = (List<?>) value;
                    if (!list.isEmpty() && list.get(0) instanceof Map) {
                        return (List<Map<String, Object>>) list;
                    }
                }
            }
        }
        return null;
    }
}
