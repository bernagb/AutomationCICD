package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageobjects.CartPage;
import pageobjects.CheckoutPage;
import pageobjects.ConfirmationPage;
import pageobjects.LandingPage;
import pageobjects.ProductCatalogue;
import rahulshettyacademy.Testcomponents.BaseTest;

public class StepDefinitionImpl extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given ("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password)
	{
		 productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When ("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When ("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) throws InterruptedException
	{
		CartPage cartpage = productCatalogue.goToCartPage();
		
		Boolean match = cartpage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartpage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then ("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string)
	{
		String message = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(message.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then ("{string} message is displayed")
	public void message_is_displayed(String string)
	{
		Assert.assertEquals(string, landingPage.getErrorMessage());
		driver.close();
	}

}
