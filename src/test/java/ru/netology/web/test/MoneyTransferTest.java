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

    @ParameterizedTest
    @CsvSource({"9999", "9998", "500", "600"})
    void shouldTransferToFirsCard(int sum) {
        var cardInfo1 = DataHelper.getCardInfo1();
        var cardInfo2 = DataHelper.getCardInfo2();
        var transferSum = DataHelper.transferSum(cardInfo2, sum);
        var dashboardPage = new DashboardPage();
        var startFirstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var startSecondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        dashboardPage.clickFirstCard()
                .depositCard(cardInfo2, transferSum);
        var finishFirstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var finishSecondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        Assertions.assertEquals(finishFirstCardBalance, startFirstCardBalance + transferSum);
        Assertions.assertEquals(finishSecondCardBalance, startSecondCardBalance - transferSum);
    }

    @ParameterizedTest
    @CsvSource({"9999", "9998", "500", "600"})
    void shouldTransferToSecondCard(int sum) {
        var cardInfo1 = DataHelper.getCardInfo1();
        var cardInfo2 = DataHelper.getCardInfo2();
        var transferSum = DataHelper.transferSum(cardInfo1, sum);
        var dashboardPage = new DashboardPage();
        var startFirstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var startSecondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        dashboardPage.clickSecondCard()
                .depositCard(cardInfo1, transferSum);
        var finishFirstCardBalance = dashboardPage.getCardBalance(cardInfo1.getCardId());
        var finishSecondCardBalance = dashboardPage.getCardBalance(cardInfo2.getCardId());
        Assertions.assertEquals(finishFirstCardBalance, startFirstCardBalance - transferSum);
        Assertions.assertEquals(finishSecondCardBalance, startSecondCardBalance + transferSum);
    }
}
