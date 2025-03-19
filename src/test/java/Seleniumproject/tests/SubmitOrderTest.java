package Seleniumproject.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObject.CartPage;
import pageObject.CheckoutPage;
import pageObject.ConfirmationPage;
import pageObject.LandingPage;
import pageObject.OrderPage;
import pageObject.ProductCatalogue;
import testsComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {

	String productName = "ADIDAS ORIGINAL";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	// public void submitOrder(String email, String password, String productName)
	// throws IOException, InterruptedException { -> 1st method process

	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException { // 2st method
																										// process

		// ProductCatalogue productCatalogue = landingPage.loginApplication(email,
		// password);->1st method process
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password")); // 2st
																														// method
																														// process

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		// driver.close();
	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("sarathkumar@gmail.com", "Zsk@152374");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}
	
	
	

	@DataProvider
	public Object[][] getData() throws IOException {
		
		List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir") +"\\src\\test\\java\\dataPart\\purchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } }; // --- Json process 3 method.
		
		// return new Object[][] {{"sarathkumar@gmail.com","Zsk@152374","ADIDAS
		// ORIGINAL"} , {"Naga@gmail.com","Kumari@1523","ADIDAS ORIGINAL"}};-> 1st
		// method process

		/*HashMap<String, String> map = new HashMap<String, String>();// 2st method process
		map.put("email", "sarathkumar@gmail.com");
		map.put("password", "Zsk@152374");
		map.put("product", "ADIDAS ORIGINAL");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "Naga@gmail.com");
		map1.put("password", "Kumari@1523");
		map1.put("product", "ADIDAS ORIGINAL");*/
	
	}
}
