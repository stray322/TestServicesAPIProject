package tests;

import io.qameta.allure.*;
import models.Entity;
import org.testng.annotations.Test;
import utils.APIEndpoints;
import utils.TestDataGenerator;
import utils.TestHelper;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для проверки функциональности обновления сущностей через API.
 * Содержит сценарии полного обновления данных сущности.
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Обновление сущностей")
public class UpdateEntityTest extends BaseTest {

    /**
     * Проверяет обновление данных существующей сущности.
     * Тест создает сущность, обновляет её случайными данными и проверяет изменения.
     */
    @Test
    @Story("Обновление сущности")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка обновления данных сущности со случайными данными")
    public void updateEntityTest() {
        Entity testEntity = TestDataGenerator.generateRandomEntity();
        Integer entityId = TestHelper.createEntityAndGetId(testEntity, requestSpec);

        Entity updateData = TestDataGenerator.generateRandomUpdateData();

        given()
                .spec(requestSpec)
                .pathParam("id", entityId)
                .body(updateData)
                .when()
                .patch(APIEndpoints.UPDATE_ENDPOINT)
                .then()
                .statusCode(204);

        Entity updatedEntity = TestHelper.getEntityById(entityId, requestSpec);

        assertThat(updatedEntity.getTitle()).isEqualTo(updateData.getTitle());
        assertThat(updatedEntity.getVerified()).isEqualTo(updateData.getVerified());
        assertThat(updatedEntity.getImportantNumbers()).isEqualTo(updateData.getImportantNumbers());
        assertThat(updatedEntity.getAddition().getAdditionalInfo())
                .isEqualTo(updateData.getAddition().getAdditionalInfo());
        assertThat(updatedEntity.getAddition().getAdditionalNumber())
                .isEqualTo(updateData.getAddition().getAdditionalNumber());

        TestHelper.deleteEntity(entityId, requestSpec);
    }
}
