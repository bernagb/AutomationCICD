package pageobjects;

import java.util.List;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	WebDriver driver;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutElement;
	
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProducts;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean VerifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckoutPage goToCheckout()
	{
		checkoutElement.click();
		return new CheckoutPage(driver);
	}
	//check the cart if ZARA product is added
			// define product name variable and use that (at the top of the class)
			//List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
			//Boolean productMatch = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
			//Assert.assertTrue(productMatch);
			//click check out button
			//driver.findElement(By.cssSelector(".totalRow button")).click();

	
	/*public WebElement getProductByName(String productName)
	{
		WebElement prod = getProductList().stream().filter(product-> product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}*/


	
	

}
