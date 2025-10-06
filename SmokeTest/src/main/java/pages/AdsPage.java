package pages;

import org.apache.tools.ant.taskdefs.Java;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class AdsPage {
    private final WebDriverWait wait;
    private WebDriver driver;
    private Actions action;


    public AdsPage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private final By priceFilterButton = By.cssSelector("[data-testid='price-filter-button']");
    private final By minimumPriceInputField = By.name("minimum_price");
    private final By maximumPriceInputField = By.name("maximum_price");

    private final By squareMetersButton = By.cssSelector("[data-testid='size-filter-button']");
    private final By minimumSizeInputField = By.name("minimum_size");
    private final By maximumSizeInputField = By.name("maximum_size");

    private final By getPropertyAds = By.xpath("//div[contains(@class, 'lazyload-wrapper') and contains(@class, 'scroll')]");
    private final By getPriceFromAd = By.cssSelector("[data-testid='property-ad-price']");
    private final By getSizeFromAdTitle = By.cssSelector("[data-testid='property-ad-title']");

    private final By dropOpenSizeBox = By.className("common-results-title-container");
    private final By nextArrowButton = By.cssSelector("button.slick-arrow.next-arrow");

    private final By propertySortingDropdownButton = By.xpath("//button[@data-testid='open-property-sorting-dropdown']");
    private final By priceDescendingButton = By.xpath("//button[@data-testid='price_desc']");

    private final By groupedAds = By.xpath("//span[@data-testid='property-ads-group']");
    private final By nestedAds = By.xpath("//div[@data-testid='unique-property-ad-container']");


    public void selectPriceFilter() {
        driver.findElement(priceFilterButton).click();
    }

    public void setMinimumPrice(int minPriceValue) {
        WebElement minInput = driver.findElement(minimumPriceInputField);
        minInput.click();
        minInput.sendKeys(String.valueOf(minPriceValue));
    }

    public void setMaximumPrice(int maxPriceValue) {
        WebElement maxInput = driver.findElement(maximumPriceInputField);
        maxInput.click();
        maxInput.sendKeys(String.valueOf(maxPriceValue));
    }

    public void selectSquareMetersSizeButton() {
        driver.findElement(squareMetersButton).click();
    }

    public void setMinimumSquareMetersSize(int minimumSquareMetersSizeValue) {
        WebElement minInput = driver.findElement(minimumSizeInputField);
        driver.findElement(minimumSizeInputField).click();
        minInput.sendKeys(String.valueOf(minimumSquareMetersSizeValue));
    }

    public void setMaximumSquareMetersSize(int maximumSquareMetersSizeValue) throws InterruptedException {
        WebElement maxInput = driver.findElement(maximumSizeInputField);
        driver.findElement(maximumSizeInputField).click();
        maxInput.sendKeys(String.valueOf(maximumSquareMetersSizeValue));
        Thread.sleep(2000);
    }

    private void scrollInTheMiddleToLoadElements() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
        Thread.sleep(3000);
    }

    public void scrollAtTheTopOfThePage() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(2000);
    }

    public void goBackwards() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.history.go(-1)");
        Thread.sleep(1000);
    }

    public void verifyAdPricesAreInSpecifiedRange(int minimumPriceValue, int maximumPriceValue) throws InterruptedException {
        List<WebElement> ads = driver.findElements(getPropertyAds);
        scrollInTheMiddleToLoadElements();

        for (WebElement getAdPrice : ads) {
            if (getAdPrice.findElements(getPriceFromAd).isEmpty()) continue;
            int price = Integer.parseInt(getAdPrice.findElement(getPriceFromAd).getText().replaceAll("[^0-9]", ""));
            Assert.assertTrue(price >= minimumPriceValue && price <= maximumPriceValue, "Price is out of range: " + price + " EUR");
        }
    }

    public void verifyPropertyAdSizeAreInSpecifiedRange(int minimumSquareMetersSizeValue, int maximumSquareMetersSizeValue) {
        List<WebElement> ads = driver.findElements(getPropertyAds);

        for (WebElement getAdSize : ads) {
            if (getAdSize.findElements(getSizeFromAdTitle).isEmpty()) continue;
            int propertySize = Integer.parseInt(getAdSize.findElement(getSizeFromAdTitle).getText().replaceAll("[^0-9]", ""));
            Assert.assertTrue(propertySize >= minimumSquareMetersSizeValue && propertySize <= maximumSquareMetersSizeValue, "Size is out of range: " + propertySize);
        }
    }

    public void verifyThatEachAdContainsASetNumberOfImages(int maximumNumberOfImagesInEachAd) throws InterruptedException {
        scrollAtTheTopOfThePage();
        WebElement dropBoxElement = wait.until(ExpectedConditions.elementToBeClickable(dropOpenSizeBox));
        action.click(dropBoxElement).perform();
        List<WebElement> ads = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getPropertyAds));

        for (WebElement hoverOnThisAd : ads) {
            action.moveToElement(hoverOnThisAd).perform();
            Thread.sleep(1000);

            int giveImageNumberOnXPath = 1;

            while (true) {
                String giveTestId = ".//img[@data-testid='ad-gallery-image-" + giveImageNumberOnXPath + "-img']";
                List<WebElement> currentAd = hoverOnThisAd.findElements(By.xpath(giveTestId));

                if (currentAd.isEmpty()) {
                    System.out.println("Total images found: " + (giveImageNumberOnXPath - 1));
                    break;
                }

                hoverOnThisAd.findElement(nextArrowButton).click();
                Thread.sleep(500);

                Assert.assertTrue(giveImageNumberOnXPath <= maximumNumberOfImagesInEachAd, "Test failed: more than 30 images in this ad");
                giveImageNumberOnXPath++;
            }
        }
    }

    public void adsAreSortedIntoDescendingOrderAndVerified() throws InterruptedException{
        scrollAtTheTopOfThePage();

        driver.findElement(propertySortingDropdownButton).click();
        driver.findElement(priceDescendingButton).click();
        Thread.sleep(5000);

        scrollInTheMiddleToLoadElements();

        int lastPrice = Integer.MAX_VALUE;

        List<WebElement> reloadedAdsOnDescendingOrder = driver.findElements(getPropertyAds);
        System.out.println("Found " + reloadedAdsOnDescendingOrder.size() + " ads to check.\n");

        for (WebElement getAdPriceInDescendingOrder : reloadedAdsOnDescendingOrder) {
            if (getAdPriceInDescendingOrder.findElements(getPriceFromAd).isEmpty()) continue;

            int getCurrentPrice = Integer.parseInt(getAdPriceInDescendingOrder.findElement(getPriceFromAd).getText().replaceAll("[^0-9]", ""));

            Assert.assertTrue(getCurrentPrice <= lastPrice,"Prices are not in descending order/Test Fail: " + lastPrice + " before " + getCurrentPrice);
            lastPrice = getCurrentPrice;
        }
    }

    public void checkIfContactPhoneIsVisibleInEachAd() throws InterruptedException {
        scrollAtTheTopOfThePage();
        List<WebElement> reloadedAdsOnDescendingOrder = driver.findElements(getPropertyAds);

        for (WebElement hoverOnCurrentAd : reloadedAdsOnDescendingOrder){
            action.moveToElement(hoverOnCurrentAd).perform();
            if (hoverOnCurrentAd.findElement(groupedAds).isDisplayed()){
                hoverOnCurrentAd.click();
                int nestedAdCount = driver.findElements(nestedAds).size();
                for (int i = 1; i < nestedAdCount; i++) {
                    WebElement nestedAd = driver.findElements(nestedAds).get(i);
                    nestedAd.click();
                    WebElement getContactButton = driver.findElement(By.xpath("//button[@data-testid='call-action-button']"));
                    Assert.assertTrue(getContactButton.isDisplayed(), "Contact button is visible, phone number is hidden");
                    goBackwards();
                }
            }
            else if (hoverOnCurrentAd.findElement(By.xpath("//span[@data-testid='property-ad-date']")).isDisplayed())
            {
                System.out.println("here");
            }
        }
    }
}
