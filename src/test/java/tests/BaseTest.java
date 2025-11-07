package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;

/**
 * Базовый класс для всех тестов API.
 * Содержит общую конфигурацию и настройки для REST assured.
 */
public class BaseTest {

    /**
     * Базовый URL API, загружаемый из конфигурации.
     */
    protected static final String BASE_URL = ConfigReader.getProperty("base.url");

    /**
     * Спецификация REST запроса, используемая всеми тестами.
     */
    protected RequestSpecification requestSpec;

    /**
     * Настраивает базовую конфигурацию перед выполнением тестового класса.
     * Конфигурирует ObjectMapper для работы с snake_case и инициализирует спецификацию запроса.
     */
    @BeforeClass
    public void setup() {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                            return mapper;
                        }));

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();

        Allure.addAttachment("Base URL", "text/uri-list", BASE_URL);
        Allure.addAttachment("Content-Type", ContentType.JSON.toString());
    }
}
