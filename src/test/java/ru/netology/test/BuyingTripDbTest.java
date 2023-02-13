package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.Card;
import ru.netology.data.DataGenerator;
import ru.netology.data.DbUtils;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuyingTripDbTest {

    Card validCard = DataGenerator.getValidCard();
    Card declinedCard = DataGenerator.getDeclinedCard();
    Card fakeCard = DataGenerator.getFakeCard();

    @BeforeEach
    public void openPage() {
        //DbUtils.clearTables();
        String url = System.getProperty("sut.url");
        open(url);
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Должен подтверждать покупку по карте со статусом APPROVED")
    void shouldConfirmPaymentWithValidCard() {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(validCard);
        paymentPage.checkForSuccessfulNotification();
        assertEquals("APPROVED", DbUtils.findPaymentStatus());
        System.out.println(DbUtils.findPaymentStatus());

    }

    @Test
    @DisplayName("Должен подтверждать кредит по карте со статусом APPROVED")
    void shouldConfirmCreditWithValidCard() {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(validCard);
        creditPage.checkForSuccessfulNotification();
        assertEquals("APPROVED", DbUtils.findCreditStatus());
        System.out.println(DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Не должен подтверждать покупку по карте со статусом DECLINED")
    void shouldNotConfirmPaymentWithDeclinedCard() {
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.checkErrorMessageIsDisplayed();
        assertEquals("DECLINED", DbUtils.findPaymentStatus());
        System.out.println(DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Не должен подтверждать кредит по карте со статусом DECLINED")
    void shouldNotConfirmCreditWithDeclinedCard() {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(declinedCard);
        creditPage.showErrorMessage();
        assertEquals("DECLINED", DbUtils.findCreditStatus());
        System.out.println(DbUtils.findCreditStatus());

    }

    @Test
    @DisplayName("Не должен подтверждать покупку по несуществующей карте")
    void shouldNotConfirmPaymentWithFakeCard() {
        DbUtils.clearTables();
        StartPage startPage = new StartPage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.fillData(fakeCard);
        paymentPage.checkErrorMessageIsDisplayed();
        assertEquals("0", DbUtils.countRecords());
        System.out.println(DbUtils.countRecords());

    }

    @Test
    @DisplayName("Не должен подтверждать кредит по несуществующей карте")
    void shouldNotConfirmCreditWithFakeCard() {
        StartPage startPage = new StartPage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(fakeCard);
        creditPage.showErrorMessage();
        assertEquals("0", DbUtils.countRecords());
        System.out.println(DbUtils.countRecords());
    }
}
