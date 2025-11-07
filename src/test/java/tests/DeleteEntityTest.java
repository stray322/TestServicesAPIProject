package tests;

import io.qameta.allure.*;
import models.Entity;
import org.testng.annotations.Test;
import utils.APIEndpoints;
import utils.TestDataGenerator;
import utils.TestHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Тесты для проверки функциональности удаления сущностей через API.
 * Содержит сценарии удаления сущностей и проверки их отсутствия после удаления.
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Удаление сущностей")
public class DeleteEntityTest extends BaseTest {

    /**
     * Проверяет корректное удаление сущности по идентификатору.
     * Тест создает сущность, удаляет её и проверяет, что сущность больше недоступна.
     */
    @Test
    @Story("Удаление сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка удаления сущности со случайными данными")
    public void deleteEntityTest() {
        Entity testEntity = TestDataGenerator.generateRandomEntity();
        Integer entityId = TestHelper.createEntityAndGetId(testEntity, requestSpec);

        given()
                .spec(requestSpec)
                .pathParam("id", entityId)
                .when()
                .get(APIEndpoints.GET_ENDPOINT)
                .then()
                .statusCode(200);

        TestHelper.deleteEntity(entityId, requestSpec);

        given()
                .spec(requestSpec)
                .pathParam("id", entityId)
                .when()
                .get(APIEndpoints.GET_ENDPOINT)
                .then()
                .statusCode(500);
    }
}
