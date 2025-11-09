package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import models.Entity;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.TestDataGenerator;
import utils.TestHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Тесты создания сущностей
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Создание сущностей")
public class CreateEntityTest extends BaseTest {

    private List<Integer> createdEntityIds;

    @BeforeMethod
    public void setUp() {
        createdEntityIds = new ArrayList<>();
    }

    @AfterMethod
    public void tearDown() {
        TestHelper.safeDeleteEntities(createdEntityIds, requestSpec);
    }

    @Test
    @Story("Создание сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка создания сущности со всеми полями")
    public void createFullEntityTest() {
        Entity testEntity = TestDataGenerator.generateFullEntity();

        Integer entityId = TestHelper.createEntity(testEntity, requestSpec);
        createdEntityIds.add(entityId);

        Entity responseEntity = TestHelper.getEntity(entityId, requestSpec);
        verifyEntitiesMatch(testEntity, responseEntity);
    }

    @Test
    @Story("Создание минимальной сущности")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка создания сущности только с обязательными полями")
    public void createMinimalEntityTest() {
        Entity testEntity = TestDataGenerator.generateMinimalEntity();

        Integer entityId = TestHelper.createEntity(testEntity, requestSpec);
        createdEntityIds.add(entityId);

        Entity responseEntity = TestHelper.getEntity(entityId, requestSpec);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(responseEntity.getTitle()).isEqualTo(testEntity.getTitle());
        softly.assertThat(responseEntity.getVerified()).isEqualTo(testEntity.getVerified());
        softly.assertAll();
    }

    private void verifyEntitiesMatch(Entity expected, Entity actual) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        softly.assertThat(actual.getVerified()).isEqualTo(expected.getVerified());
        softly.assertThat(actual.getImportantNumbers()).isEqualTo(expected.getImportantNumbers());

        if (expected.getAddition() != null) {
            softly.assertThat(actual.getAddition()).isNotNull();
            softly.assertThat(actual.getAddition().getAdditionalInfo())
                    .isEqualTo(expected.getAddition().getAdditionalInfo());
            softly.assertThat(actual.getAddition().getAdditionalNumber())
                    .isEqualTo(expected.getAddition().getAdditionalNumber());
        }

        softly.assertAll();
    }
}
