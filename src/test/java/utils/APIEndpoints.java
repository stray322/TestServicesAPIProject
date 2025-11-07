package utils;

/**
 * Класс констант для хранения эндпоинтов API.
 * Централизованное хранение путей API для предотвращения дублирования.
 */
public class APIEndpoints {

    /**
     * Эндпоинт для создания новой сущности.
     */
    public static final String CREATE_ENDPOINT = "/create";

    /**
     * Эндпоинт для получения сущности по идентификатору.
     */
    public static final String GET_ENDPOINT = "/get/{id}";

    /**
     * Эндпоинт для получения списка всех сущностей с пагинацией.
     */
    public static final String GET_ALL_ENDPOINT = "/getAll";

    /**
     * Эндпоинт для частичного обновления сущности по идентификатору.
     */
    public static final String UPDATE_ENDPOINT = "/patch/{id}";

    /**
     * Эндпоинт для удаления сущности по идентификатору.
     */
    public static final String DELETE_ENDPOINT = "/delete/{id}";
}
