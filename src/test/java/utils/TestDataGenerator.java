package utils;

import models.Addition;
import models.Entity;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Генератор случайных тестовых данных для сущностей.
 * Обеспечивает создание разнообразных тестовых данных для предотвращения эффекта пестицида.
 */
public class TestDataGenerator {
    private static final Random random = new Random();
    private static final String[] TEXTS = {"Test Data", "Sample Info", "Demo Content", "Random Text", "Test Info", "Additional Data"};
    private static final Boolean[] BOOLEANS = {true, false};
    private static final String[] TITLES = {"Entity", "Object", "Item", "Record", "Data Point"};

    /**
     * Генерирует случайную сущность для тестирования с уникальными данными.
     *
     * @return Entity со случайно сгенерированными значениями всех полей
     * @throws AssertionError если не удалось сгенерировать сущность
     */
    public static Entity generateRandomEntity() {
        try {
            return Entity.builder()
                    .title(generateRandomTitle())
                    .verified(generateRandomBoolean())
                    .importantNumbers(generateRandomNumbers())
                    .addition(generateRandomAddition())
                    .build();
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать случайную сущность", e);
        }
    }

    /**
     * Генерирует случайные данные для обновления существующей сущности.
     *
     * @return Entity с обновленными случайными значениями
     * @throws AssertionError если не удалось сгенерировать данные для обновления
     */
    public static Entity generateRandomUpdateData() {
        try {
            return Entity.builder()
                    .title("Updated " + generateRandomTitle())
                    .verified(generateRandomBoolean())
                    .importantNumbers(generateRandomNumbers())
                    .addition(generateRandomAddition())
                    .build();
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать данные для обновления сущности", e);
        }
    }

    /**
     * Генерирует сущность только с обязательными полями.
     *
     * @return Entity с минимальным набором данных
     * @throws AssertionError если не удалось сгенерировать минимальную сущность
     */
    public static Entity generateMinimalEntity() {
        try {
            return Entity.builder()
                    .title(generateRandomTitle())
                    .verified(generateRandomBoolean())
                    .build();
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать минимальную сущность", e);
        }
    }

    /**
     * Генерирует уникальное название для сущности.
     *
     * @return строка с уникальным названием
     * @throws AssertionError если не удалось сгенерировать название
     */
    private static String generateRandomTitle() {
        try {
            String baseTitle = TITLES[random.nextInt(TITLES.length)];
            return baseTitle + "_" + System.currentTimeMillis() + "_" + random.nextInt(1000);
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать название сущности", e);
        }
    }

    /**
     * Генерирует случайное булево значение.
     *
     * @return случайное true или false
     * @throws AssertionError если не удалось сгенерировать булево значение
     */
    private static Boolean generateRandomBoolean() {
        try {
            return BOOLEANS[random.nextInt(BOOLEANS.length)];
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать булево значение", e);
        }
    }

    /**
     * Генерирует список случайных чисел.
     *
     * @return список из 1-5 случайных чисел в диапазоне 1-100
     * @throws AssertionError если не удалось сгенерировать список чисел
     */
    private static List<Integer> generateRandomNumbers() {
        try {
            int count = random.nextInt(5) + 1;
            return IntStream.range(0, count)
                    .map(i -> random.nextInt(100) + 1)
                    .boxed()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать список чисел", e);
        }
    }

    /**
     * Генерирует случайные дополнительные данные.
     *
     * @return Addition со случайными значениями
     * @throws AssertionError если не удалось сгенерировать дополнительные данные
     */
    private static Addition generateRandomAddition() {
        try {
            return Addition.builder()
                    .additionalInfo(TEXTS[random.nextInt(TEXTS.length)] + " " + random.nextInt(100))
                    .additionalNumber(random.nextInt(1000))
                    .build();
        } catch (Exception e) {
            throw new AssertionError("Не удалось сгенерировать дополнительные данные", e);
        }
    }
}
