package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OrderPage {

    private WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы1

    // Поле "Имя"
    private By inputName = By.cssSelector("input[placeholder='* Имя']");

    // Поле "Фамилия"
    private By inputSurname = By.cssSelector("input[placeholder='* Фамилия']");

    // Поле "Адрес"
    private By inputAdress = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");

    // Поле "Станция метро"
    private By inputSelectStation = By.className("select-search__input");

    // Выбранная Станция метро
    private By inputMetroOption = By.xpath("//button/div[text()='Сокольники']");

    // Поле "Телефон"
    private By inputPhone = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка Далее
    private By continueButton = By.xpath("//button[text()='Далее' and contains(@class,'Button_Button__ra12g Button_Middle__1CSJM')]");


    // Локаторы2

    // Поле "Когда привезти самокат?"
    private By  inputSelectDate = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    // Поле "Срок аренды"
    private By inputSelectRentPeriod = By.xpath("//div[contains(@class,'Dropdown-control')]//div[contains(text(),'Срок аренды')]");

    // Выбрать период аренды
    private By selectRentOption = By.xpath("//div[@class='Dropdown-menu']/div[text()='сутки']");

    // Выбрать цвет самоката
    private By selectColorOption = By.id("grey");

    // Поле "Комментарий для курьера"
    private By inputCommentary = By.cssSelector("input[placeholder='Комментарий для курьера']");

    // Кнопка Заказать
    private By orderButton = By.xpath("//button[text()='Заказать' and contains(@class,'Button_Button__ra12g Button_Middle__1CSJM')]");

    // Кнопка Назад
    private By backButton = By.xpath("//button[text()='Назад']");

    // Окно подтверждения "Хотите оформить заказ?"
    private By confirmOrderPage = By.className("Order_ModalHeader__3FDaJ");

    // Кнопка "Да" в окне подтверждения
    private By confirmOrderButton = By.xpath("//button[text()='Да']");

    // Окно "Заказ оформлен"
    private By orderSuccessPopup = By.xpath("//*[contains(text(), 'Заказ оформлен')]");



    // Методы1
    public void enterName(String name) {
        driver.findElement(inputName).sendKeys(name);
    }

    public void enterSurname(String surname) {
        driver.findElement(inputSurname).sendKeys(surname);
    }

    public void enterAdress(String adress) {
        driver.findElement(inputAdress).sendKeys(adress);
    }

    public void enterMetroStation() {
        driver.findElement(inputSelectStation).click();
    }

    public void selectMetroStation() {
        driver.findElement(inputMetroOption).click();
    }

    public void enterPhone(String phone) {
        driver.findElement(inputPhone).sendKeys(phone);
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }



    // Методы2

    public void enterDate(String date) {
        driver.findElement(inputSelectDate).click();
        By dayLocator = By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + date + "']");
        driver.findElement(dayLocator).click();

    }

    public void enterRentPeriod (String period){
        driver.findElement(inputSelectRentPeriod).click();
        By option = By.xpath("//div[@class='Dropdown-option' and text()='" + period + "']");
        driver.findElement(option).click();
    }

    public void enterColor(String color) {
        By colorOption = By.id(color);
        driver.findElement(colorOption).click();
    }

    public void clickOrderButton(){
        WebElement button = driver.findElement(orderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }


    // в Chrome баг - не нажимается кнопка "Да" в окне подтверждения
    public void clickConfirmOrderButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrderPage));
        driver.findElement(confirmOrderButton).click();
    }

    public boolean isOrderSuccessPopupVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessPopup));
        return popup.isDisplayed();
    }


}
