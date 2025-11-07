package utils;

import io.qameta.allure.Allure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Утилита для работы с конфигурационными параметрами.
 * Обеспечивает загрузку параметров из файла config.properties и централизованный доступ к настройкам.
 */
public class ConfigReader{

    /**
     * Объект Properties для хранения загруженных параметров конфигурации.
     */
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                String errorMsg = "Файл config.properties не найден в classpath";
                Allure.addAttachment("Ошибка", "text/plain", errorMsg);
                throw new AssertionError(errorMsg);
            }

            properties.load(input);
            Allure.addAttachment("Загруженные параметры", properties.toString());

        } catch (IOException e) {
            String errorMsg = String.format("Ошибка чтения config.properties: %s", e.getMessage());
            Allure.addAttachment("Исключение", "text/plain", errorMsg);
            throw new AssertionError(errorMsg, e);
        }
    }

    /**
     * Получает значение параметра по ключу.
     *
     * @param key ключ параметра в формате "section.property"
     * @return значение параметра в виде строки
     * @throws AssertionError если параметр не найден в конфигурации
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            String errorMsg = String.format("Параметр '%s' отсутствует в config.properties", key);
            Allure.addAttachment("Не найден параметр", "text/plain", errorMsg);
            throw new AssertionError(errorMsg);
        }

        Allure.addAttachment("Используемый параметр",
                String.format("Ключ: %s\nЗначение: %s", key, value));
        return value;
    }
}
