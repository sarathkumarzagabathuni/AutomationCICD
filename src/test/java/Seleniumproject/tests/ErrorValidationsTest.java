package Seleniumproject.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import pageObject.CartPage;
import pageObject.CheckoutPage;
import pageObject.ConfirmationPage;
import pageObject.ProductCatalogue;
import testsComponents.BaseTest;

public class ErrorValidationsTest extends BaseTest {

	/*@Test
	public void submitOrder() throws IOException, InterruptedException {

		String productName = "ADIDAS ORIGINAL";

		landingPage.loginApplication("sarathkumar@gmail.com", "Zsk@152");
		
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	}*/
@Test(groups= {"ErrorHandling"},retryAnalyzer = testsComponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		String productName = "ADIDAS ORIGINAL";

		landingPage.loginApplication("sarathkumar@gmail.com", "Zsk@1523");
		
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ADIDAS ORIGINAL";

		ProductCatalogue productCatalogue = landingPage.loginApplication("sarathkumar@gmail.com", "Zsk@152374");

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay("ADIDAS ORIGINAL 1");
		Assert.assertFalse(match);

	}

}
