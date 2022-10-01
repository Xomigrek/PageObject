package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    void openDashboardPage() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        open("http://localhost:9999");
        new LoginPage()
                .validLogin(authInfo)
                .validVerify(verificationCode);
    }

    //    @Test
//    void shouldTransferMoneyBetweenOwnCardsV1() {
//        open("http://localhost:9999");
//        var loginPage = new LoginPage();
////    var loginPage = open("http://localhost:9999", LoginPageV1.class);
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        verificationPage.validVerify(verificationCode);
//    }
    @ParameterizedTest
    @CsvSource({"-100", "100"})
    void shouldTransferToFirsCard(int sum) {
        var cardInfo1 = DataHelper.getCardInfo1();
        var cardInfo2 = DataHelper.getCardInfo2();
        var dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var secondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        dashboardPage.depositFirstCard(cardInfo2, sum)
                .checkBalance(cardInfo1)
                .checkBalance(cardInfo2);
    }

    @ParameterizedTest
    @CsvSource({"10001", "10002"})
    void shouldTransferToSecondCard(int sum) {
        var cardInfo1 = DataHelper.getCardInfo1();
        var cardInfo2 = DataHelper.getCardInfo2();
        var dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var secondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        dashboardPage.depositSecondCard(cardInfo1, sum)
                .checkBalance(cardInfo1)
                .checkBalance(cardInfo2);
    }
}
