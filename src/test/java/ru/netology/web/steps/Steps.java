package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class Steps {
    private static LoginPage loginPage;
    private static DashboardPage dash;
    private static VerificationPage verify;
    private static TransferPage transfer;

    @Пусть ("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword (String login, String password) {
        loginPage = Selenide.open("http://localhost:9999" , LoginPage.class);
        verify = loginPage.validLogin(login, password);
        dash = verify.validVerify("12345");
    }
    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transferToFirstCard (String sum, String cardNumber) {
        transfer = dash.clickFirstCard();
        dash = transfer.depositCard(sum, cardNumber);
    }

    @Тогда("баланс его карты {string} из списка на главной странице должен стать {string} рублей")
    public void checkBalance (String id, String balance) {
        Assertions.assertEquals(StringUtils.deleteWhitespace(balance), Integer.toString(dash.checkCardBalance(id)));
    }
}
