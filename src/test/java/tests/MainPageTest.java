package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObject.MainPage;

import java.util.stream.Stream;

public class MainPageTest {

    private WebDriver driver;

    static Stream<Arguments> browserProvider() {
        return Stream.of(
                Arguments.of("chrome"),
                Arguments.of("firefox")
        );
    }

    static Stream<Arguments> faqIndexProvider() {
        return Stream.of(
                Arguments.of("chrome", 0),
                Arguments.of("chrome", 1),
                Arguments.of("chrome", 2),
                Arguments.of("chrome", 3),
                Arguments.of("chrome", 4),
                Arguments.of("chrome", 5),
                Arguments.of("chrome", 6),
                Arguments.of("chrome", 7),
                Arguments.of("firefox", 0),
                Arguments.of("firefox", 1),
                Arguments.of("firefox", 2),
                Arguments.of("firefox", 3),
                Arguments.of("firefox", 4),
                Arguments.of("firefox", 5),
                Arguments.of("firefox", 6),
                Arguments.of("firefox", 7)
        );
    }

    // Тест: главная страница открывается
    @ParameterizedTest
    @MethodSource("browserProvider")
    void testOpenMainPage(String browser) {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Тест: FAQ — ответ появляется
    @ParameterizedTest
    @MethodSource("faqIndexProvider")
    void testFaqAnswerVisible(String browser, int index) {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.get("https://qa-scooter.praktikum-services.ru/");
        MainPage mainPage = new MainPage(driver);

        mainPage.closeBannerIfVisible();
        mainPage.scrollToFaqAndClick(index);
        String answer = mainPage.getFaqAnswerTextWithWait(index);
        Assertions.assertFalse(answer.isEmpty(), "Ответ на вопрос пустой");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}