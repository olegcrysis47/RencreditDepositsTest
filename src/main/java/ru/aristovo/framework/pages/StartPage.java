package ru.aristovo.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Класс старотовой страницы сайта Ренессанс Кредит Банк.
 */
public class StartPage extends BasePage {

    /**
     * Переменная для хранения списка доступного меню на сайте
     */
    @FindBy(xpath = "//div[@class='service__title-text']")
    List<WebElement> startMenuSite;

    /**
     * Метод для открытия нужного пункта меню.
     * @param menuTitle - пользователь указывает меню, в которое он хочет попасть (в данном тесте "Вклады").
     *                  Если будет указан пункт меню, которого нет на странице, то тест закончится ошибкой.
     * @return DepositPage - метод возвращает страницу с вкладами и проверяет заголовок открывшейся страницы.
     */
    @Step("Выбираем в основном меню сайта пункт '{menuTitle}'")
    public DepositPage choiceMenuOnStartPage(String menuTitle) {
        for (WebElement w : startMenuSite) {
            if (w.getText().equalsIgnoreCase(menuTitle)) {
                WebElement clickMenu = w.findElement(By.xpath(".//following-sibling::a"));
                clickMenu.click();
                return app.getDepositPage().checkedTitlePage(menuTitle);
            }
        }
        Assert.fail("На основной странице сайта меню '" + menuTitle + "' не найдено!");
        return app.getDepositPage();
    }

}
