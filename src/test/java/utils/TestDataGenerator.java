package utils;

import io.qameta.allure.Step;
import models.Addition;
import models.Entity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Генератор тестовых данных для сущностей.
 * Обеспечивает создание случайных и уникальных данных для тестирования.
 */
public final class TestDataGenerator {

    private static final Random RANDOM = new Random();
    private static final AtomicLong UNIQUE_COUNTER = new AtomicLong(System.currentTimeMillis());
    private static final String[] TITLES = {"Entity", "Object", "Item", "Record"};
    private static final String[] INFO_TEXTS = {"Test", "Sample", "Demo", "Random"};

    /**
     * Приватный конструктор для предотвращения создания экземпляров утильного класса.
     */
    private TestDataGenerator() {
        throw new UnsupportedOperationException("Это утильный класс и не может быть инстанциирован");
    }

    /**
     * Генерирует сущность с указанными параметрами.
     *
     * @param includeNumbers true - включает важные числа, false - исключает
     * @param includeAddition true - включает дополнительную информацию, false - исключает
     * @return сгенерированная сущность
     */
    @Step("Сгенерировать сущность")
    public static Entity generateEntity(boolean includeNumbers, boolean includeAddition) {
        Entity.EntityBuilder builder = Entity.builder()
                .title(generateUniqueTitle())
                .verified(RANDOM.nextBoolean());

        if (includeNumbers) {
            builder.importantNumbers(generateNumbers());
        }

        if (includeAddition) {
            builder.addition(generateAddition());
        }

        return builder.build();
    }

    /**
     * Генерирует полную сущность со всеми полями.
     *
     * @return полная сущность с числами и дополнительной информацией
     */
    @Step("Сгенерировать полную сущность")
    public static Entity generateFullEntity() {
        return generateEntity(true, true);
    }

    /**
     * Генерирует данные для обновления сущности.
     *
     * @return сущность с обновленными данными
     */
    @Step("Сгенерировать данные для обновления")
    public static Entity generateUpdateData() {
        return Entity.builder()
                .title("Updated_" + generateUniqueTitle())
                .verified(RANDOM.nextBoolean())
                .importantNumbers(generateNumbers())
                .addition(generateAddition())
                .build();
    }

    /**
     * Генерирует минимальную сущность только с обязательными полями.
     *
     * @return минимальная сущность без чисел и дополнительной информации
     */
    @Step("Сгенерировать минимальную сущность")
    public static Entity generateMinimalEntity() {
        return generateEntity(false, false);
    }

    /**
     * Генерирует уникальный заголовок для сущности.
     *
     * @return уникальный заголовок
     */
    private static String generateUniqueTitle() {
        long uniqueId = UNIQUE_COUNTER.incrementAndGet();
        return TITLES[RANDOM.nextInt(TITLES.length)] + "_" + uniqueId;
    }

    /**
     * Генерирует список случайных чисел.
     *
     * @return список из 1-3 случайных чисел
     */
    private static List<Integer> generateNumbers() {
        return IntStream.range(0, RANDOM.nextInt(3) + 1)
                .map(i -> RANDOM.nextInt(100) + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Генерирует дополнительную информацию для сущности.
     *
     * @return объект с дополнительной информацией
     */
    private static Addition generateAddition() {
        return Addition.builder()
                .additionalInfo(INFO_TEXTS[RANDOM.nextInt(INFO_TEXTS.length)] + "_info_" +
                        UNIQUE_COUNTER.get())
                .additionalNumber(RANDOM.nextInt(1000))
                .build();
    }
}
