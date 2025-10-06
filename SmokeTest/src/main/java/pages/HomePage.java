package pages;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BaseTest {
    private WebDriver driver;
    private final WebDriverWait wait;

    private final By acceptCookiesButton = By.id("accept-btn");
    private final By propertyTransactionTypeDropdown = By.cssSelector("[data-testid='open-property-transaction-dropdown']");
    private final By rentalPropertyButtonOnDropdown = By.xpath("//button[@data-testid='rent']");
    private final By propertyTypeDropdown = By.cssSelector("[data-testid='open-property-type-dropdown']");
    private final By selectResidenceButtonOnDropdown = By.xpath("//button[@data-testid='re_residence']");
    private final By areaInput = By.xpath("//input[@data-testid='area-input']");
    private final By areaSuggestions = By.xpath("//div[@data-testid='geo_place_id_dropdown_panel']//button");
    private final By searchButton = By.cssSelector("[data-testid='submit-input']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void acceptCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
    }

    public void selectPropertyTransactionDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(propertyTransactionTypeDropdown)).click();
    }

    public void selectRentalPropertyOnDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(rentalPropertyButtonOnDropdown)).click();
    }

    public void selectPropertyTypeOnDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(propertyTypeDropdown)).click();
    }

    public void selectResidence() {
        wait.until(ExpectedConditions.elementToBeClickable(selectResidenceButtonOnDropdown)).click();
    }

    public void enterAreaAndSelectAllSuggestions(String areaName) throws InterruptedException {

        WebElement areaField = wait.until(ExpectedConditions.elementToBeClickable(areaInput));
        areaField.clear();
        areaField.sendKeys(areaName);
        Thread.sleep(3000);

        List<WebElement> suggestions = driver.findElements(areaSuggestions);
        int countSuggestions = suggestions.size();

        areaField.sendKeys(Keys.ARROW_UP);
        for (int i = 0; i < countSuggestions; i++) {
            areaField.sendKeys(Keys.ARROW_DOWN);
            areaField.sendKeys(Keys.ENTER);
            areaField.sendKeys(areaName);
            Thread.sleep(3000);
        }
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
}
