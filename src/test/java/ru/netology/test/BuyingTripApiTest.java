package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.ApiUtils;
import ru.netology.data.Card;
import ru.netology.data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuyingTripApiTest {

    Card invalidHolderCard = DataGenerator.getInvalidHolderCard();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Не должен отправлять запрос на оплату с некорректным именем владельца")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        String response = ApiUtils.getHeaderName(invalidHolderCard, "/api/v1/pay");
        assertEquals("DECLINED", response);
    }

    @Test
    @DisplayName("Не должен отправлять запрос на кредит с некорректным именем владельца")
    void shouldNotSendCreditRequestWithIncorrectName() {
        String response = ApiUtils.getHeaderName(invalidHolderCard, "/api/v1/credit");
        assertEquals("DECLINED", response);
    }

    @Test
    @DisplayName("Должен провести оплату за услуги с валидной карты")
    void shouldCheckValidBuyerPayment() {
        String response = ApiUtils.getHeaderName(DataGenerator.getValidCard(), "/api/v1/pay");
        assertEquals("APPROVED", response);
    }

    @Test
    @DisplayName("Должен провести оплату при покупке услуги в кредит")
    void shouldCheckValidBuyerCreditPayment() {
        String response = ApiUtils.getHeaderName(DataGenerator.getValidCard(), "/api/v1/credit");
        assertEquals("APPROVED", response);
    }

    @Test
    @DisplayName("Не должен провести оплату при покупке услуги в кредит")
    void shouldCheckDeclinedCreditPayment() {
        String response = ApiUtils.getHeaderName(DataGenerator.getDeclinedCard(), "/api/v1/credit");
        assertEquals("DECLINED", response);
    }

    @Test
    @DisplayName("Не должен провести оплату при покупке услуги в кредит")
    void shouldCheckDeclinedDebitPayment() {
        String response = ApiUtils.getHeaderName(DataGenerator.getDeclinedCard(), "/api/v1/pay");
        assertEquals("DECLINED", response);
    }
}
