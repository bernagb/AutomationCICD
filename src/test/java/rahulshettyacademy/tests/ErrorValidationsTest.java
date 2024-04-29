package rahulshettyacademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;



import pageobjects.ProductCatalogue;
import rahulshettyacademy.Testcomponents.BaseTest;
import rahulshettyacademy.Testcomponents.Retry;
public class ErrorValidationsTest extends BaseTest{

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException {
		
		
		//give wrong password and email
		landingPage.loginApplication("bgb@gmail.com", "3456&b");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	
		

	}

}
