package ru.netology.web.data;

import lombok.Value;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selenide.$$;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardsInfo {
        private String card;
        private String cardId;
    }

    public static CardsInfo getCardInfo1() {
        return new CardsInfo("5559000000000001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardsInfo getCardInfo2() {
        return new CardsInfo("5559000000000002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static int transferSum(CardsInfo info, int sum) {
        DashboardPage dash = new DashboardPage();
        var balance = dash.getCardBalance(info.getCardId());
        int transfer = balance - sum;
        if (transfer > balance || transfer < 0) {
            transfer = balance;
        }
        return transfer;
    }
}