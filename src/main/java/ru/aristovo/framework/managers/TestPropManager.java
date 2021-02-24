package ru.aristovo.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для управления пропертями
 */
public class TestPropManager {

    /**
     * Переменна для хранения считанных из файла пропертей
     *
     * @see Properties - реализован на основе {@link java.util.Hashtable}
     */
    private final Properties properties = new Properties();

    /**
     * Переменна для хранения объекта TestPropManager
     */
    private static TestPropManager INSTANCE = null;

    /**
     * Конструктор специально запривейтили (синглтон)
     * Происходит загрузка содержимого файла application.properties в {@link #properties}
     *
     * @see TestPropManager#getTestPropManager()
     */
    private TestPropManager() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/" +
                            System.getProperty("env", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод ленивой инициализации TestPropManager
     *
     * @return TestPropManager - возвращает TestPropManager
     */
    public static TestPropManager getTestPropManager() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropManager();
        }
        return INSTANCE;
    }

    /**
     * Метод возвращает либо значение записанное в ключе в переменной {@link #properties},
     * либо дефолтное значение при отсутствие ключа в переменной {@link #properties}
     *
     * @param key          - ключ, значения которого хотите получить
     * @param defaultValue - дефолтное значение которое хотите получить если отсутствует ключ в {@link #properties}
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Метод возвращает значения записанное в ключе в переменной {@link #properties}, если нет перемнной вернет null
     *
     * @param key - ключ, значения которого хотите получить
     * @return String - строка со значением ключа
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
