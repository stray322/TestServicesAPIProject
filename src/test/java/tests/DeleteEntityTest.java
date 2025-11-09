package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import models.Entity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.TestDataGenerator;
import utils.TestHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты удаления сущностей
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Удаление сущностей")
public class DeleteEntityTest extends BaseTest {

    private Integer entityIdForDeleteTest;
    private Integer entityIdForRepeatTest;

    @BeforeMethod
    public void setUp() {
        Entity testEntity1 = TestDataGenerator.generateFullEntity();
        Entity testEntity2 = TestDataGenerator.generateFullEntity();

        entityIdForDeleteTest = TestHelper.createEntity(testEntity1, requestSpec);
        entityIdForRepeatTest = TestHelper.createEntity(testEntity2, requestSpec);
    }

    @AfterMethod
    public void tearDown() {
        TestHelper.safeDeleteEntity(entityIdForDeleteTest, requestSpec);
        TestHelper.safeDeleteEntity(entityIdForRepeatTest, requestSpec);
    }

    @Test
    @Story("Удаление сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка удаления сущности по ID")
    public void deleteEntityTest() {
        Entity existingEntity = TestHelper.getEntity(entityIdForDeleteTest, requestSpec);
        assertThat(existingEntity).isNotNull();

        TestHelper.deleteEntity(entityIdForDeleteTest, requestSpec);

        assertThat(TestHelper.isEntityExists(entityIdForDeleteTest, requestSpec)).isFalse();
    }

    @Test
    @Story("Повторное удаление")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка поведения при повторном удалении сущности")
    public void deleteAlreadyDeletedEntityTest() {
        TestHelper.deleteEntity(entityIdForRepeatTest, requestSpec);

        given()
                .spec(requestSpec)
                .pathParam("id", entityIdForRepeatTest)
                .when()
                .delete(utils.APIEndpoints.DELETE_ENDPOINT)
                .then()
                .statusCode(is(500));
    }
}
