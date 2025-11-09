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

/**
 * Тесты обновления сущностей
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Обновление сущностей")
public class UpdateEntityTest extends BaseTest {

    private Integer testEntityId;

    @BeforeMethod
    public void setUp() {
        Entity testEntity = TestDataGenerator.generateFullEntity();
        testEntityId = TestHelper.createEntity(testEntity, requestSpec);
    }

    @AfterMethod
    public void tearDown() {
        TestHelper.safeDeleteEntity(testEntityId, requestSpec);
    }

    @Test
    @Story("Обновление сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка полного обновления сущности")
    public void updateEntityTest() {
        Entity originalEntity = TestHelper.getEntity(testEntityId, requestSpec);
        Entity updateData = TestDataGenerator.generateUpdateData();

        TestHelper.updateEntity(testEntityId, updateData, requestSpec);
        Entity updatedEntity = TestHelper.getEntity(testEntityId, requestSpec);

        verifyEntityUpdated(updatedEntity, updateData);
    }

    private void verifyEntityUpdated(Entity actual, Entity expected) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        softly.assertThat(actual.getVerified()).isEqualTo(expected.getVerified());
        softly.assertThat(actual.getImportantNumbers()).isEqualTo(expected.getImportantNumbers());

        if (expected.getAddition() != null) {
            softly.assertThat(actual.getAddition().getAdditionalInfo())
                    .isEqualTo(expected.getAddition().getAdditionalInfo());
            softly.assertThat(actual.getAddition().getAdditionalNumber())
                    .isEqualTo(expected.getAddition().getAdditionalNumber());
        }

        softly.assertAll();
    }
}
