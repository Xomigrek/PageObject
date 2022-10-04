package ru.netology.web.page;

import com.codeborne.selenide.Condition;
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
        val text = cards.findBy(Condition.text(id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage clickFirstCard() {
        depositButton1.click();
        return new TransferPage();
    }

    public TransferPage clickSecondCard() {
        depositButton2.click();
        return new TransferPage();
    }

    public int checkCardBalance (String id) {
        var balance = getCardBalance(id);
        return balance;
    }
}
