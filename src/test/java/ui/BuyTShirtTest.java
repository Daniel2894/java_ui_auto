package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import page.*;

public class BuyTShirtTest {
    WebDriver driver = new ChromeDriver();

    MenuContentPage menuContentPage = new MenuContentPage(driver);
    CataloguePage cataloguePage = new CataloguePage(driver);
    ProductDetailPage productDetailPage = new ProductDetailPage(driver);
    CheckoutPage checkoutPage = new CheckoutPage(driver);
    SummaryPage summaryPage = new SummaryPage(driver);
    SignInPage signInPage = new SignInPage(driver);
    AddressPage addressPage = new AddressPage(driver);
    ShippingPage shippingPage = new ShippingPage(driver);
    PaymentPage paymentPage = new PaymentPage(driver);
    BankPaymentPage bankPaymentPage = new BankPaymentPage(driver);
    OrderResumePage orderResumePage = new OrderResumePage(driver);

    @BeforeTest
    public void setUp() {
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    public void shouldBuyATShirt(){
        menuContentPage.clickTShirtOption();
        cataloguePage.selectProduct("Faded Short Sleeve T-shirts");
        productDetailPage.addToCart();

        checkoutPage.proceedToCheckout();
        summaryPage.proceedToCheckout();
        signInPage.signIn("aperdomobo@gmail.com", "WorkshopProtractor");
        addressPage.proceedToCheckout();
        shippingPage.agreeTermsOfService();
        shippingPage.proceedToCheckout();
        paymentPage.payByBankWire();

        bankPaymentPage.confirmOrder();

        // Assertion with just TestNG (Without Hamcrest):
        Assert.assertEquals(orderResumePage.getOrderConfirmation(),"Your order on My Store is complete.");

        // Assertion with Hamcrest:
        assertThat(orderResumePage.getOrderConfirmation(), equalTo("Your order on My Store is complete."));
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }
}
