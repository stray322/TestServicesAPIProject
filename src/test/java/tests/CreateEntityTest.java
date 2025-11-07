package tests;

import io.qameta.allure.*;
import models.Entity;
import org.testng.annotations.Test;
import utils.TestDataGenerator;
import utils.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для проверки функциональности создания сущностей через API.
 * Содержит позитивные сценарии создания сущностей со случайными данными.
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Создание сущностей")
public class CreateEntityTest extends BaseTest {

    /**
     * Проверяет создание новой сущности со случайными данными.
     * Тест включает полную проверку всех полей созданной сущности.
     */
    @Test
    @Story("Создание сущности")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка создания новой сущности со случайными данными")
    public void createEntityTest() {
        Entity testEntity = TestDataGenerator.generateRandomEntity();

        Integer entityId = TestHelper.createEntityAndGetId(testEntity, requestSpec);

        Entity responseEntity = TestHelper.getEntityById(entityId, requestSpec);

        assertThat(responseEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(responseEntity.getVerified()).isEqualTo(testEntity.getVerified());
        assertThat(responseEntity.getImportantNumbers()).isEqualTo(testEntity.getImportantNumbers());
        assertThat(responseEntity.getAddition().getAdditionalInfo())
                .isEqualTo(testEntity.getAddition().getAdditionalInfo());
        assertThat(responseEntity.getAddition().getAdditionalNumber())
                .isEqualTo(testEntity.getAddition().getAdditionalNumber());

        TestHelper.deleteEntity(entityId, requestSpec);
    }
}
