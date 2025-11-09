package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Вспомогательные методы для работы с API.
 * Содержит методы для выполнения CRUD операций с сущностями.
 */
public final class TestHelper {

    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);
    private static final int HTTP_OK = 200;
    private static final int HTTP_NO_CONTENT = 204;

    private TestHelper() {
        throw new UnsupportedOperationException("Утильный класс не может быть инстанциирован");
    }

    /**
     * Создает новую сущность в системе.
     */
    @Step("Создать сущность")
    public static Integer createEntity(Entity entity, RequestSpecification spec) {
        String response = given()
                .spec(spec)
                .body(entity)
                .when()
                .post(APIEndpoints.CREATE_ENDPOINT)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .asString();

        return Integer.parseInt(response.trim());
    }

    /**
     * Получает сущность по идентификатору.
     */
    @Step("Получить сущность по ID {entityId}")
    public static Entity getEntity(Integer entityId, RequestSpecification spec) {
        return given()
                .spec(spec)
                .pathParam("id", entityId)
                .when()
                .get(APIEndpoints.GET_ENDPOINT)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .as(Entity.class);
    }

    /**
     * Обновляет существующую сущность.
     */
    @Step("Обновить сущность {entityId}")
    public static void updateEntity(Integer entityId, Entity updateData, RequestSpecification spec) {
        given()
                .spec(spec)
                .pathParam("id", entityId)
                .body(updateData)
                .when()
                .patch(APIEndpoints.UPDATE_ENDPOINT)
                .then()
                .statusCode(HTTP_NO_CONTENT);
    }

    /**
     * Удаляет сущность по идентификатору.
     */
    @Step("Удалить сущность {entityId}")
    public static void deleteEntity(Integer entityId, RequestSpecification spec) {
        given()
                .spec(spec)
                .pathParam("id", entityId)
                .when()
                .delete(APIEndpoints.DELETE_ENDPOINT)
                .then()
                .statusCode(HTTP_NO_CONTENT);
    }

    /**
     * Получает список всех сущностей.
     * Реальная структура ответа: {"entity": [...], "page": X, "perPage": Y}
     */
    @Step("Получить список сущностей")
    public static List<Entity> getAllEntities(RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .get(APIEndpoints.GET_ALL_ENDPOINT)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .jsonPath()
                .getList("entity", Entity.class);
    }

    /**
     * Проверяет существование сущности в системе.
     */
    @Step("Проверить существование сущности {entityId}")
    public static boolean isEntityExists(Integer entityId, RequestSpecification spec) {
        if (entityId == null) {
            return false;
        }

        try {
            Response response = given()
                    .spec(spec)
                    .pathParam("id", entityId)
                    .when()
                    .get(APIEndpoints.GET_ENDPOINT);

            return response.getStatusCode() == HTTP_OK;
        } catch (Exception e) {
            logger.debug("Сущность {} не существует: {}", entityId, e.getMessage());
            return false;
        }
    }

    /**
     * Безопасно удаляет сущность, проверяя ее существование перед удалением.
     */
    @Step("Безопасное удаление сущности {entityId}")
    public static void safeDeleteEntity(Integer entityId, RequestSpecification spec) {
        if (entityId == null) return;

        if (isEntityExists(entityId, spec)) {
            try {
                deleteEntity(entityId, spec);
                logger.info("Сущность {} успешно удалена", entityId);
            } catch (Exception e) {
                logger.warn("Ошибка при удалении сущности {}: {}", entityId, e.getMessage());
            }
        } else {
            logger.debug("Сущность {} уже удалена или не существует", entityId);
        }
    }

    /**
     * Безопасно удаляет список сущностей.
     */
    @Step("Безопасное удаление списка сущностей")
    public static void safeDeleteEntities(List<Integer> entityIds, RequestSpecification spec) {
        if (entityIds == null || entityIds.isEmpty()) return;

        entityIds.forEach(entityId -> safeDeleteEntity(entityId, spec));
    }
}
