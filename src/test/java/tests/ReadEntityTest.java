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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты чтения сущностей
 */
@Epic("API Тесты для работы с сущностями")
@Feature("Чтение сущностей")
public class ReadEntityTest extends BaseTest {

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
    @Story("Чтение сущности")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка получения сущности по ID")
    public void getEntityByIdTest() {
        Entity entity = TestHelper.getEntity(testEntityId, requestSpec);

        assertThat(entity)
                .isNotNull()
                .satisfies(e -> {
                    assertThat(e.getId()).isEqualTo(testEntityId);
                    assertThat(e.getTitle()).isNotBlank();
                    assertThat(e.getVerified()).isNotNull();
                });
    }

    @Test
    @Story("Получение списка сущностей")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка получения списка всех сущностей")
    public void getAllEntitiesTest() {
        List<Entity> entities = TestHelper.getAllEntities(requestSpec);

        // Реальная структура: {"entity": [...], "page": X, "perPage": Y}
        // Проверяем что метод возвращает корректный список из поля "entity"
        assertThat(entities).isNotNull();

        // Если список не пустой, проверяем структуру сущностей
        if (!entities.isEmpty()) {
            Entity firstEntity = entities.get(0);
            assertThat(firstEntity)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getId()).isNotNull();
                        assertThat(e.getTitle()).isNotBlank();
                    });
        }
    }

    @Test
    @Story("Проверка структуры сущности")
    @Severity(SeverityLevel.MINOR)
    @Description("Валидация структуры полученной сущности")
    public void validateEntityStructureTest() {
        Entity responseEntity = TestHelper.getEntity(testEntityId, requestSpec);

        assertThat(responseEntity)
                .isNotNull()
                .satisfies(e -> {
                    assertThat(e.getId()).isNotNull();
                    assertThat(e.getTitle()).isNotBlank();
                    assertThat(e.getVerified()).isNotNull();
                });
    }
}
