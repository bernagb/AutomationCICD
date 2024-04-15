package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		//System.setProperty("webdriver.chrome.driver", "C:/Users/Gökçe/OneDrive/Documents/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");
		LandingPage landingPage = new LandingPage(driver); 
		//login to webpage
		driver.findElement(By.id("userEmail")).sendKeys("bgbg@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("B123456&b");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(7));
		//add wait to load all the products on the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-lg-4")));
		
		//gather all the products
		List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));
		
		//add zara product to chart by checking the texts
		//texts are in tag b
		WebElement prod = products.stream().filter(product-> product.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		//click to add chart button > use prod as driver object
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card-body button:last-of-type")));
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//wait for the confirmation message to appear 
		//and loading icon to be disappear before clicking cart button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// to avoid the performance issue of Selenium use invisibilityOF(webElement)
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		//check the cart if ZARA product is added
		// define product name variable and use that (at the top of the class)
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean productMatch = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(productMatch);
		//click check out button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//send country name using actions class, select from dropdown.
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		// wait results to appear on the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.cssSelector(".ta-results button:nth-child(3)")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		//verify the thank you message at the top of the page
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

}
