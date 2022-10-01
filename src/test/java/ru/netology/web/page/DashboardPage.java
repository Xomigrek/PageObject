package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amount = $("[data-test-id=\"amount\"] .input__control");
    private SelenideElement from = $("[data-test-id=\"from\"] .input__control");
    private SelenideElement transfer = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancel = $("[data-test-id=\"action-cancel\"]");
    private SelenideElement depositButton1 = $$("[data-test-id=\"action-deposit\"]").first();
    private SelenideElement depositButton2 = $$("[data-test-id=\"action-deposit\"]").last();
    private SelenideElement reload = $("[data-test-id=\"action-reload\"]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage reload() {
        reload.click();
        return this;
    }

    public int getCardBalance(String id) {
        val text = cards.findBy(attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public DashboardPage depositFirstCard(DataHelper.CardsInfo info, int sum) {
        var balance = getCardBalance(info.getCardId());
        var transferSum = balance - sum;
        if (transferSum > balance || transferSum < 0) {
            transferSum = balance;
        }
        depositButton1.click();
        amount.setValue(String.valueOf(transferSum));
        from.setValue(info.getCard());
        transfer.click();
        return this;
    }

    public DashboardPage depositSecondCard(DataHelper.CardsInfo info, int sum) {
        var balance = getCardBalance(info.getCardId());
        var transferSum = balance - sum;
//        if (transferSum > balance || transferSum < 0) {
//            transferSum = balance;
//        }
        depositButton2.click();
        amount.setValue(String.valueOf(transferSum));
        from.setValue(info.getCard());
        transfer.click();
        return this;
    }

    public DashboardPage checkBalance(DataHelper.CardsInfo info) {
        cards.findBy(attribute("data-test-id", info.getCardId())).shouldBe(visible);
        var balance = getCardBalance(info.getCardId());
        Assertions.assertTrue(balance > 0);
        return this;
    }
}
