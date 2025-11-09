package utils;

/**
 * Класс констант для хранения эндпоинтов API.
 * Централизованное хранение путей API для предотвращения дублирования.
 */
public class APIEndpoints {

    /**
     * Эндпоинт для создания новой сущности.
     * Использует HTTP POST метод.
     */
    public static final String CREATE_ENDPOINT = "/create";

    /**
     * Эндпоинт для получения сущности по идентификатору.
     * Использует HTTP GET метод.
     * Требует параметр пути {id}.
     */
    public static final String GET_ENDPOINT = "/get/{id}";

    /**
     * Эндпоинт для получения списка всех сущностей с пагинацией.
     * Использует HTTP GET метод.
     * Поддерживает параметры запроса: title, verified, page, perPage.
     */
    public static final String GET_ALL_ENDPOINT = "/getAll";

    /**
     * Эндпоинт для частичного обновления сущности по идентификатору.
     * Использует HTTP PATCH метод.
     * Требует параметр пути {id}.
     */
    public static final String UPDATE_ENDPOINT = "/patch/{id}";

    /**
     * Эндпоинт для удаления сущности по идентификатору.
     * Использует HTTP DELETE метод.
     * Требует параметр пути {id}.
     */
    public static final String DELETE_ENDPOINT = "/delete/{id}";

    /**
     * Приватный конструктор для предотвращения создания экземпляров утильного класса.
     */
    private APIEndpoints() {
        throw new UnsupportedOperationException("Это утильный класс и не может быть инстанциирован");
    }
}
