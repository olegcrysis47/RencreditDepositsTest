package ru.aristovo.tests;

import io.qameta.allure.Description;
import org.junit.Test;
import ru.aristovo.base.BaseTests;

public class DepositTest extends BaseTests {

    @Test
    @Description("Тестируем рублевый депозитный калькулятор на сайте банка Ренессанс Кредит")
    public void depositRUBCalculateTest() {
        app.getStartPage()
                .choiceMenuOnStartPage("вклады")
                .choiceCurrencyDeposit("рубли")
                .choicePlaceDepositRegistration("в отделении банка")
                .fillFieldDeposit("Сумма вклада", "300 000")
                .selectTimeMonth(6)
                .fillFieldDeposit("Ежемесячное пополнение", "50 000")
                .setupCapitalization()
                .checkedResultDataDeposit("Ставка", "4.20%")
                .checkedResultDataDeposit("Начислено %", "8 918,20")
                .checkedResultDataDeposit("К снятию", "558 918,20");

        /*
        Сценарий 1 (Использовать Allure + JUnit + Java + Selenium)
        1. Перейти по ссылке - https://rencredit.ru
        2. Перейти в меню – Вклады
        3. Выбрать – Рубли
        4. Сумма вклада – 300 000
        5. Срок – 6 месяцев
        6. Ежемесячное пополнение – 50 000
        7. Отметить – Ежемесячная капитализация
        8. Проверить расчеты по вкладу (Начислено %, Пополнение за 6 месяцев, К снятию через 6 месяцев)
         */

        // удалить ожидание по готовности
        waitThreadInTest(3000);
    }


    @Test
    @Description("Тестируем долларовый депозитный калькулятор на сайте банка Ренессанс Кредит")
    public void depositUSDCalculateTest() {
        app.getStartPage()
                .choiceMenuOnStartPage("вклады")
                .choiceCurrencyDeposit("доллары США")
                .choicePlaceDepositRegistration("в отделении банка")
                .fillFieldDeposit("Сумма вклада", "500 000")
                .selectTimeMonth(12)
                .fillFieldDeposit("Ежемесячное пополнение", "70 000")
                .setupCapitalization()
                .checkedResultDataDeposit("Ставка", "0.15%")
                .checkedResultDataDeposit("Начислено %", "1 003,59")
                .checkedResultDataDeposit("К снятию", "831 003,59");

        /*
        Сценарий 2 (Использовать Allure + JUnit + Java + Selenium)
        1. Перейти по ссылке - https://rencredit.ru
        2. Перейти в меню – Вклады
        3. Выбрать – Доллары США
        4. Сумма вклада – 500 000
        5. Срок – 12 месяцев
        6. Ежемесячное пополнение – 70 000
        7. Отметить – Ежемесячная капитализация
        8. Проверить расчеты по вкладу
         */

        // удалить ожидание по готовности
        waitThreadInTest(3000);
    }

    // временно
    public void waitThreadInTest(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
