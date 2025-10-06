package tests;

import utils.TestData;
import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdsPage;
import pages.HomePage;

public class SmokeTest extends BaseTest {

    private HomePage homePage;
    private AdsPage adsPage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(driver);
        adsPage = new AdsPage(driver);
    }

    @Test (priority = 1)
    public void theUserSearchesForRentalResidence() throws InterruptedException{
        Thread.sleep(3000);
        homePage.acceptCookies();
        homePage.selectPropertyTransactionDropdown();
        homePage.selectRentalPropertyOnDropdown();
        homePage.selectPropertyTypeOnDropdown();
        homePage.selectResidence();
    }

    @Test(priority = 2)
    public void theUserSearchesForSpecificAreaAndSelectsSimilarAreasFromSuggestions() throws InterruptedException{
        homePage.enterAreaAndSelectAllSuggestions(TestData.AREA_NAME);
        homePage.clickSearchButton();
        System.out.println("debug here if bot control appears");
    }

    @Test(priority = 3)
    public void andTheUserSetsAPrice() throws InterruptedException{
        adsPage.selectPriceFilter();
        adsPage.setMinimumPrice(TestData.MINIMUM_PRICE);
        adsPage.setMaximumPrice(TestData.MAXIMUM_PRICE);

        adsPage.selectSquareMetersSizeButton();
        adsPage.setMinimumSquareMetersSize(TestData.MINIMUM_SQUARE_METERS);
        adsPage.setMaximumSquareMetersSize(TestData.MAXIMUM_SQUARE_METERS);
        Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void andTheUserVerifiesThatEachAdContainsSpecifiedPriceAndSize() throws InterruptedException{
        adsPage.verifyAdPricesAreInSpecifiedRange(TestData.MINIMUM_PRICE, TestData.MAXIMUM_PRICE);
        adsPage.verifyPropertyAdSizeAreInSpecifiedRange(TestData.MINIMUM_SQUARE_METERS, TestData.MAXIMUM_SQUARE_METERS);
    }

    @Test(priority = 5)
    public void andTheUserVerifiesThatEachAdContainsASetNumberOfImages() throws InterruptedException{
        adsPage.verifyThatEachAdContainsASetNumberOfImages(TestData.MAXIMUM_IMAGES_PER_AD);
    }

    @Test(priority = 6)
    public void andTheUserSortsTheAdsByDescendingPriceAndVerifiesThatTheyAreCorrectlySorted() throws InterruptedException{
        adsPage.adsAreSortedIntoDescendingOrderAndVerified();
    }
    @Test(priority = 7)
    public void verifyThatEachAdDoesNotHaveAVisiblePhoneContact() throws InterruptedException{
        adsPage.checkIfContactPhoneIsVisibleInEachAd();
    }
}
