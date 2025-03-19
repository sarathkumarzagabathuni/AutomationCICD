package stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.CartPage;
import pageObject.CheckoutPage;
import pageObject.ConfirmationPage;
import pageObject.LandingPage;
import pageObject.ProductCatalogue;
import testsComponents.BaseTest;

public class StepDefinationImplement extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page") // just a static no regular expression. this is plane static no dynamic values
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage =launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")    // this is regular expression and it is in form regex.
	public void Logged_in_with_username_and_password(String username, String password) {
	productCatalogue = landingPage.loginApplication(username,password);
		
	}
	
	@When("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException {

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName) throws InterruptedException{
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
		Thread.sleep(5000);
        checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}
	@Then("{string} message is displayed on ConfirmationPage") //this is plane static but there are dynamic values, Here the dynamic value is already sitting in the step.
	public void message_is_displayed_on_ConfirmationPage(String string) {
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close(); 
		
	} 
	@Then("^\"([^\"]*)\" message is displayed$")
	public void something_message_is_displayed(String strArg1) {
		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		driver.close();
	}
     
}
