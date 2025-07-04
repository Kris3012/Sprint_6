package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPage;
import pageObject.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.provider.Arguments;

public class OrderPageTest {

    private WebDriver driver;

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    public void orderPositiveTest(String name, String surname, String address,
                                  String phone, String day, String rentPeriod, String color, boolean useTopButton) {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://qa-scooter.praktikum-services.ru/");
        MainPage mainPage = new MainPage(driver);
        mainPage.closeBannerIfVisible();

        // Нажать на кнопку заказа вверху или внизу
        if (useTopButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);

        // Заполнить первую страницу заказа
        orderPage.enterName(name);
        orderPage.enterSurname(surname);
        orderPage.enterAdress(address);
        orderPage.enterMetroStation();
        orderPage.selectMetroStation();
        orderPage.enterPhone(phone);
        orderPage.clickContinueButton();

        // Заполнить вторую страницу заказа
        orderPage.enterDate(day);
        orderPage.enterRentPeriod(rentPeriod);
        orderPage.enterColor(color);
        orderPage.clickOrderButton();

        // Подтвердить заказ
        orderPage.clickConfirmOrderButton();

        // Проверка успешного оформления заказа
        assertTrue(orderPage.isOrderSuccessPopupVisible());
    }

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(
                Arguments.of("Глаша", "Петрова", "ул. Цветочная, д. 5", "+79998887766", "2", "сутки", "grey", true),
                Arguments.of("Дмитрий", "Смирнов", "ул. Яблоневая, д. 12", "+79997776655", "5", "двое суток", "black", false)
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
