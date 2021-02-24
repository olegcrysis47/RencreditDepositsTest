package ru.aristovo.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static ru.aristovo.framework.managers.DriverManager.getDriver;

/**
 * Класс страницы сайта с вкладами/депозитами.
 */
public class DepositPage extends BasePage {

    /**
     * Переменная для прокрутки до заголовка калькулятора вклада/депозита.
     */
    @FindBy(xpath = "//h2[contains(.,'Рассчитайте доходность по вкладу')]")
    WebElement titleCalculateDeposit;

    /**
     * Переменная хранит список веб-элементов с валютами вклада.
     * @see #choiceCurrencyDeposit(String)
     */
    @FindBy(xpath = "//input[@type='radio']")
    List<WebElement> listCurrency;

    /**
     * Переменная для выбора оформления депозита в отделении банка.
     * @see #choicePlaceDepositRegistration(String)
     */
    @FindBy(xpath = "//div[@class='calculator__check-row-field check-deposit']")
    WebElement officeBankButton;

    /**
     * Переменная для выбора оформления депозита в интернет-банке.
     * @see #choicePlaceDepositRegistration(String)
     */
    @FindBy(xpath = "//div[@class='calculator__check-row-field check-deposit js-no-foreign-currency']")
    WebElement internetBankButton;

    /**
     * Переменная для поля "Сумма вклада".
     */
    @FindBy(xpath = "//div//input[@name='amount']")
    WebElement depositAmount;

    /**
     * Переменная для поля "Ежемесячное пополнение".
     */
    @FindBy(xpath = "//div//input[@name='replenish']")
    WebElement monthlyAdd;

    /**
     * Переменная для хранения переключателя срока депозита.
     */
    @FindBy(xpath = "//select")
    WebElement timeMonthDeposit;

    /**
     * Переменная для проверки переключения срока депозита на нужный срок пользователя.
     */
    @FindBy(xpath = "//div[@class='jq-selectbox__select-text']")
    WebElement checkedTimeMonthDeposit;

    /**
     * Переменная веб-элемента для проверки процентной ставки депозита.
     */
    @FindBy(xpath = "//span[@class='js-calc-rate']")
    WebElement rateDeposit;

    /**
     * Переменная веб-элемента для проверки начисленных процентов депозита.
     */
    @FindBy(xpath = "//span[@class='js-calc-earned']")
    WebElement earnedDeposit;

    /**
     * Переменная веб-элемента для проверки итоговой суммы к снятию депозита.
     */
    @FindBy(xpath = "//span[@class='js-calc-result']")
    WebElement resultDeposit;

    /**
     * Метод проверяет на правильную страницу был осуществлен переход или нет.
     * @param titlePage - ожидаемый заголовок страницы сайта.
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("Проверка: заголовок страницы должен быть '{titlePage}'")
    public DepositPage checkedTitlePage(String titlePage) {
        Assert.assertEquals("Открылась неверная страница",
                titlePage.toLowerCase(),
                getDriver().getTitle().toLowerCase());
        return this;
    }

    /**
     * Метод для выбора валюты вклада.
     * @param customCurrency - пользователь указывает в какой валюте будет вклад.
     *                       В настоящий момент доступны "Рубли" и "Доллары США"
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("Выбираем валюту '{customCurrency}' в которой будет рассчитан депозит")
    public DepositPage choiceCurrencyDeposit(String customCurrency) {
        scrollToElementJs(titleCalculateDeposit);
        String accessCurrency = null; // Переменная для доступных валют на сайте
        switch (customCurrency.toLowerCase()) {
            case "рубли":
                accessCurrency = "RUB";
                break;
            case "доллары сша":
                accessCurrency = "USD";
                break;
            default:
                Assert.fail("Введеная валюта '" + customCurrency + "' отсутствует на сайте");
                return this;
        }
        for (WebElement w : listCurrency) {
            if (w.getAttribute("value").equals(accessCurrency)) {
                WebElement btnCurrency = w.findElement(By.xpath("./.."));
                btnCurrency.click();
                return this;
            }
        }
        Assert.fail("На сайте изменился список валют или их написание. Валюта '" + customCurrency + "' не найдена.");
        return this;
    }

    /**
     * Метод для выбора места оформления вклада/депозита.
     * @param place - пользователь указывает место оформления вклада.
     *              На данный момент доступны: "В отделении банка" и "В интернет-банке".
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("Выбираем место '{place}' где будет оформлен вклад")
    public DepositPage choicePlaceDepositRegistration(String place) {
        WebElement placeRegistration;
        switch (place.toLowerCase()) {
            case "в отделении банка":
                placeRegistration = officeBankButton;
                break;
            case "в интернет-банке":
                placeRegistration = internetBankButton;
                break;
            default:
                Assert.fail("Введено не верное место '" + place + "' оформления кредита.");
                return this;
        }
        WebElement placeButton = placeRegistration.findElement(By.xpath(".//div[@unselectable]"));
        if (!placeButton.getAttribute("class").equals("jq-checkbox calculator__check checked")) {
            placeButton.click();
        }
        Assert.assertEquals("Нужное место оформление депозита '" + place + "' не включилось!",
                "jq-checkbox calculator__check checked",
                placeButton.getAttribute("class"));
        return this;
    }

    /**
     * Метод для заполнения полей в калькуляторе вклада/депозита.
     * @param fieldName - поле, которое заполняет пользователь.
     * @param amount - данные, которые пользователь вносит в поле.
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("В поле '{fieldName}' вносим данные '{amount}'")
    public DepositPage fillFieldDeposit(String fieldName, String amount) {
        WebElement field = null; // поле с суммами, которое планируем
        switch (fieldName) {
            case "Сумма вклада":
                field = depositAmount;
                break;
            case "Ежемесячное пополнение":
                field = monthlyAdd;
                break;
            default:
                Assert.fail("Поле '" + fieldName + "' на странице не найдено!");
                return this;
        }
        fillField(field, amount);
        Assert.assertEquals("Поле '" + fieldName + "' заполнилось не верно",
                amount,
                field.getAttribute("value"));
        return this;
    }

    /**
     * Метод для установки желаемого срока депозита.
     * @param month - пользователь указывает срок депозита в месяцах.
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("Устанавливаем срок депозита в '{month}' месяц(ев)")
    public DepositPage selectTimeMonth(int month) {
        String value = null; // переменная для установки select
        String time = null;// переменная для проверки правильности установки срока
        switch (month) {
            case 3:
                value = "3";
                time = "3 месяца";
                break;
            case 6:
                value = "6";
                time = "6 месяцев";
                break;
            case 9:
                value = "9";
                time = "9 месяцев";
                break;
            case 12:
                value = "12";
                time = "12 месяцев";
                break;
            case 13:
                value = "13";
                time = "13 месяцев";
                break;
            case 18:
                value = "18";
                time = "18 месяцев";
                break;
            default:
                Assert.fail("Депозит на срок '" + month + "' месяца не доступен.");
                return this;
        }
        Select select = new Select(timeMonthDeposit);
        select.selectByValue(value);
        waitThread(1000); // добавил задержку для переключения
        // ГЛЮЧИТ на 13 и 18 месяцах, автоматом сбрасывает на 12.
        Assert.assertEquals("В поле прогрузился не верный срок депозита", time, checkedTimeMonthDeposit.getText());
        return this;
    }

    /**
     * ПЕРЕНЕСТИ ВВЕРХ когда метод setupCapitalization начнет работать.
     * Переменная для переключения капитализации процентов.
     */
    @FindBy(xpath = "//input[@name='capitalization']/..") // в ПЕРВОМ ВАРИАНТЕ БЫЛ //input[@name='capitalization']
    WebElement capitalization;

    /**
     * ДОБАВИТЬ @STEP
     * МЕТОД НЕ ДОДЕЛАН. Должен при необходимости проверять и вкл/выкл капитализацию процентов.
     * СЕЙЧАС только включает втупую.
     * @return
     */
    public DepositPage setupCapitalization() {
        capitalization.click();
        /* //ПОПЫТКИ установки и проверки капитализации не сработали (СПРОСИТЬ)
        WebElement w = capitalization.findElement(By.xpath("./.."));
        if (!(w.getAttribute("class").equals("jq-checkbox calculator__check checked"))) {
            capitalization.click();
        }
        Assert.assertEquals("Капитализация не включилась", "jq-checkbox calculator__check checked", w.getAttribute("class"));
        */
        waitThread(1000);
        return this;
    }

    /**
     * Производим проверку правильности расчета калькулятора депозита.
     * @param field - поле, которое хотим проверить.
     * @param expResult - ожидаемый результат.
     * @return - остатемся на текущей странице с вкладами.
     */
    @Step("В поле '{field}' ожидаем результат '{expResult}'")
    public DepositPage checkedResultDataDeposit(String field, String expResult) {
        WebElement element = null;
        switch (field) {
            case "Ставка":
                element = rateDeposit;
                break;
            case "Начислено %":
                element = earnedDeposit;
                break;
            case "К снятию":
                element = resultDeposit;
                break;
            default:
                Assert.fail("Такого поля '" + field + "' нет на проверке.");
        }
        Assert.assertEquals("Поле '" + field + "' не прошло проверку.",
                expResult,
                element.getText());
        return this;
    }

}
