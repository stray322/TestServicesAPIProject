package tests;

import io.qameta.allure.*;
import models.Entity;
import org.testng.annotations.Test;
import utils.APIEndpoints;
import utils.TestDataGenerator;
import utils.TestHelper;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

/**
 * Тесты для проверки функциональности чтения сущностей через API.
 * Содержит тесты получения отдельных сущностей и списка всех сущностей.
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Чтение сущностей")
public class ReadEntityTest extends BaseTest {

    /**
     * Проверяет получение данных конкретной сущности по идентификатору.
     * Выполняет полную проверку всех полей полученной сущности.
     */
    @Test
    @Story("Чтение сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка получения данных сущности со случайными данными")
    public void getEntityTest() {
        Entity testEntity = TestDataGenerator.generateRandomEntity();
        Integer entityId = null;

        try {
            entityId = TestHelper.createEntityAndGetId(testEntity, requestSpec);
            Entity responseEntity = TestHelper.getEntityById(entityId, requestSpec);

            assertThat(responseEntity.getTitle())
                    .withFailMessage("Заголовок сущности не соответствует ожидаемому")
                    .isEqualTo(testEntity.getTitle());

            assertThat(responseEntity.getVerified())
                    .withFailMessage("Статус верификации сущности не соответствует ожидаемому")
                    .isEqualTo(testEntity.getVerified());

            assertThat(responseEntity.getImportantNumbers())
                    .withFailMessage("Список важных чисел не соответствует ожидаемому")
                    .isEqualTo(testEntity.getImportantNumbers());

            assertThat(responseEntity.getAddition().getAdditionalInfo())
                    .withFailMessage("Дополнительная информация не соответствует ожидаемой")
                    .isEqualTo(testEntity.getAddition().getAdditionalInfo());

            assertThat(responseEntity.getAddition().getAdditionalNumber())
                    .withFailMessage("Дополнительное число не соответствует ожидаемому")
                    .isEqualTo(testEntity.getAddition().getAdditionalNumber());

        } finally {
            if (entityId != null) {
                TestHelper.deleteEntity(entityId, requestSpec);
            }
        }
    }

    /**
     * Проверяет получение списка всех сущностей с пагинацией.
     * Адаптивный тест, который работает с разными структурами ответа.
     */
    @Test
    @Story("Список сущностей")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверка получения списка сущностей с проверкой структуры")
    public void getAllEntitiesTest() {
        Entity testEntity = TestDataGenerator.generateRandomEntity();
        Integer entityId = TestHelper.createEntityAndGetId(testEntity, requestSpec);

        try {
            Object response = given()
                    .spec(requestSpec)
                    .queryParam("page", 1)
                    .queryParam("perPage", 10)
                    .when()
                    .get(APIEndpoints.GET_ALL_ENDPOINT)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Object.class);

            Allure.addAttachment("Тип ответа getAll", "text/plain",
                    response.getClass().getName());
            Allure.addAttachment("Структура ответа getAll", "text/plain",
                    response.toString());

            List<Map<String, Object>> entities;

            if (response instanceof List) {
                entities = (List<Map<String, Object>>) response;
            } else if (response instanceof Map) {
                Map<String, Object> responseMap = (Map<String, Object>) response;

                if (responseMap.containsKey("content") && responseMap.get("content") instanceof List) {
                    entities = (List<Map<String, Object>>) responseMap.get("content");
                } else if (responseMap.containsKey("data") && responseMap.get("data") instanceof List) {
                    entities = (List<Map<String, Object>>) responseMap.get("data");
                } else if (responseMap.containsKey("items") && responseMap.get("items") instanceof List) {
                    entities = (List<Map<String, Object>>) responseMap.get("items");
                } else if (responseMap.containsKey("entities") && responseMap.get("entities") instanceof List) {
                    entities = (List<Map<String, Object>>) responseMap.get("entities");
                } else {
                    entities = findFirstListInMap(responseMap);
                }
            } else {
                throw new AssertionError("Неизвестная структура ответа от /getAll");
            }

            assertThat(entities)
                    .withFailMessage("Список сущностей не должен быть пустым после создания тестовой сущности")
                    .isNotEmpty();

            Map<String, Object> firstEntity = entities.get(0);
            Allure.addAttachment("Первый элемент списка", "application/json",
                    firstEntity.toString());

            if (firstEntity.containsKey("id")) {
                assertThat(firstEntity.get("id"))
                        .withFailMessage("ID сущности не должен быть null")
                        .isNotNull();
            }

            if (firstEntity.containsKey("title")) {
                assertThat(firstEntity.get("title"))
                        .withFailMessage("Заголовок сущности не должен быть null")
                        .isNotNull();
            }

            assertThat(firstEntity)
                    .withFailMessage("Сущность должна содержать хотя бы одно поле")
                    .isNotEmpty();

        } finally {
            TestHelper.deleteEntity(entityId, requestSpec);
        }
    }

    /**
     * Вспомогательный метод для поиска первого списка в Map.
     *
     * @param map объект для поиска
     * @return первый найденный список или null, если список не найден
     */
    private List<Map<String, Object>> findFirstListInMap(Map<String, Object> map) {
        for (Object value : map.values()) {
            if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                if (!list.isEmpty() && list.get(0) instanceof Map) {
                    return list;
                }
            }
        }
        return null;
    }

    /**
     * Упрощенный тест для проверки базовой функциональности получения списка.
     * Не создает тестовых данных, проверяет только статус ответа.
     */
    @Test
    @Story("Базовый список сущностей")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверка получения пустого или непустого списка сущностей")
    public void getAllEntitiesBasicTest() {
        given()
                .spec(requestSpec)
                .queryParam("page", 1)
                .queryParam("perPage", 10)
                .when()
                .get(APIEndpoints.GET_ALL_ENDPOINT)
                .then()
                .statusCode(200)
                .body("", is(notNullValue()));
    }

    /**
     * Тест для проверки получения списка сущностей без пагинации.
     */
    @Test
    @Story("Список без пагинации")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверка получения списка сущностей без параметров пагинации")
    public void getAllEntitiesWithoutPaginationTest() {
        Object response = given()
                .spec(requestSpec)
                .when()
                .get(APIEndpoints.GET_ALL_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(Object.class);

        Allure.addAttachment("Ответ без пагинации", "text/plain", response.toString());

        assertThat(response)
                .withFailMessage("Ответ от /getAll без пагинации не должен быть null")
                .isNotNull();
    }
}
