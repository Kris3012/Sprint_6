package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPage;

import java.util.stream.Stream;

public class MainPageTest {

    private WebDriver driver;

    static Stream<Arguments> faqProvider() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объясните причину и отмените заказ."),
                Arguments.of(7, "Да, доставим. Стоимость доставки за МКАД — обсуждается индивидуально.")
        );
    }

    @ParameterizedTest
    @MethodSource("faqProvider")
    void testFaqAnswerText(int index, String expectedAnswer) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://qa-scooter.praktikum-services.ru/");
        MainPage mainPage = new MainPage(driver);

        mainPage.closeBannerIfVisible();
        mainPage.scrollToFaqAndClick(index);
        String actualAnswer = mainPage.getFaqAnswerTextWithWait(index);

        Assertions.assertEquals(expectedAnswer, actualAnswer, "Текст ответа не совпадает с ожидаемым");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
