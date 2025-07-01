package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы

    // Кнопка "Заказать" вверху страницы
    private By topOrderButton = By.xpath("//div[contains(@class,'Header')]/button[text()='Заказать']");

    // Кнопка "Заказать" внизу страницы
    private By bottomOrderButton = By.xpath("//button[text()='Заказать' and contains(@class,'Button_Middle__1CSJM')]");

    // Кнопка баннера
    private By cookieBannerButton = By.className("App_CookieButton__3cvqF");

    // FAQ вопрос по номеру
    private By faqQuestion(int index) {
        return By.id("accordion__heading-" + index);
    }

    // FAQ ответ по номеру
    private By faqAnswer(int index) {
        return By.id("accordion__panel-" + index);
    }

    // Методы

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        driver.findElement(bottomOrderButton).click();
    }

    public void clickFaqQuestion(int index) {
        driver.findElement(faqQuestion(index)).click();
    }

    public String getFaqAnswerText(int index) {
        return driver.findElement(faqAnswer(index)).getText();
    }

    public void scrollToFaqAndClick(int index) {
        WebElement question = driver.findElement(faqQuestion(index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        question.click();
    }

    public String getFaqAnswerTextWithWait(int index) {
        By answerLocator = faqAnswer(index);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return driver.findElement(answerLocator).getText();
    }

    public void closeBannerIfVisible() {
        try {
            WebElement banner = driver.findElement(cookieBannerButton);
            if (banner.isDisplayed()) {
                banner.click();
            }
        } catch (Exception e) {
        }
    }
}