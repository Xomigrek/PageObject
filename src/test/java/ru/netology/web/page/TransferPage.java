package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.security.PublicKey;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amount = $("[data-test-id=\"amount\"] .input__control");
    private SelenideElement from = $("[data-test-id=\"from\"] .input__control");
    private SelenideElement transfer = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancel = $("[data-test-id=\"action-cancel\"]");

    public TransferPage () {heading.shouldBe(visible);
    }

    public DashboardPage depositCard (String sum, String cardNumber) {
        amount.setValue(sum);
        from.setValue(cardNumber);
        transfer.click();
        return new DashboardPage();
    }
}
