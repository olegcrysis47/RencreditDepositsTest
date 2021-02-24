package ru.aristovo.framework.managers;

import ru.aristovo.framework.pages.*;

/**
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    StartPage startPage;

    /**
     * Страница с расчетом депозита
     */
    DepositPage depositPage;

    /**
     * Конструктор специально запривейтили (синглтон)
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link ru.aristovo.framework.pages.StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    /**
     * Ленивая инициализация {@link ru.aristovo.framework.pages.DepositPage}
     *
     * @return DepositPage
     */
    public DepositPage getDepositPage() {
        if (depositPage == null) {
            depositPage = new DepositPage();
        }
        return depositPage;
    }
}
