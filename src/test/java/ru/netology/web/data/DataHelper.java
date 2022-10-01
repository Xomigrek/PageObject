package ru.netology.web.data;

import lombok.Value;

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
        private String CardMask;


    }

    public static CardsInfo getCardInfo1() {
        return new CardsInfo("5559000000000001", $$(".list__item div").first().getAttribute("data-test-id"),"0001");
    }

    public static CardsInfo getCardInfo2() {
        return new CardsInfo("5559000000000002", $$(".list__item div").last().getAttribute("data-test-id"),"0002");
    }
}