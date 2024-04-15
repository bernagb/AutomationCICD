package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.CartPage;
import pageobjects.CheckoutPage;
import pageobjects.ConfirmationPage;
import pageobjects.LandingPage;
import pageobjects.OrderPage;
import pageobjects.ProductCatalogue;
import rahulshettyacademy.Testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
		
		
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
	
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartpage = productCatalogue.goToCartPage();
		
		Boolean match = cartpage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartpage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String message = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(message.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	//verify ZARA COAT 3 is displaying in orders page
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("bgbg@gmail.com", "B123456&b");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{	
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+ "\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	//Other DataProvider methods but the optimized way is above. getJsonDataToMap can be used for other json files as well?
	
	/*@DataProvider
	public Object[][] getData()
	{
		return new Object[][] {{"bgbg@gmail.com", "B123456&b","ZARA COAT 3"},{"shetty@gmail.com","Iamking@000",
			"ADIDAS ORIGINAL"}};
	}*/
	
	
	/*@DataProvider
	/*HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "bgbg@gmail.com");
		map.put("password", "B123456&b");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "ADIDAS ORIGINAL");
		return new Object[][] {{map},{map1}};*/

}
