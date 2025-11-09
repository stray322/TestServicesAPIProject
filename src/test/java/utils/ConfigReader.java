package utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Утилита для работы с конфигурационными параметрами.
 * Обеспечивает загрузку и доступ к настройкам из файла config.properties.
 */
public class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    /**
     * Загружает свойства из файла config.properties.
     *
     * @throws IllegalStateException если файл не найден или произошла ошибка загрузки
     */
    private static void loadProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Файл config.properties не найден в classpath");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка загрузки config.properties", e);
        }
    }

    /**
     * Получает значение параметра по ключу.
     *
     * @param key ключ параметра
     * @return значение параметра
     * @throws IllegalArgumentException если параметр отсутствует в config.properties
     */
    public static String getProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Параметр '" + key + "' отсутствует в config.properties");
        }
        return value;
    }
}
