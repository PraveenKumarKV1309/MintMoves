package screens;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import driver.CreateSession;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import logger.Log;
import utilites.GenericMethods;


public class LoginScreen extends CreateSession{
	
		public WebDriver driver;
	
		//public AppiumDriver driver;
		public GenericMethods genericMethods;
		//private static final Logger logger = (Logger) LogManager.getLogger(LoginScreen.class);
		
	 	@AndroidFindBy(xpath ="//*[@content-desc='UI']")
	    private WebElement uiButton;
	 	
	 	
	 	@AndroidFindBy(xpath ="//android.view.View[@content-desc='Next']")
	    private WebElement nextButton;
	 	
	 	@AndroidFindBy(xpath ="//android.view.View[@content-desc=\"Lets ‘Move’\"]")
	    private WebElement letsMovebutton;
	 	
	 	@AndroidFindBy(xpath ="(//*[@class='android.widget.ImageView'])[2]")
	    private WebElement emailTextBox;
	 	
	 	@AndroidFindBy(className ="android.widget.EditText")
	    private WebElement passwordTextBox;
	 	
	 	@AndroidFindBy(xpath ="(//*[@content-desc='Login'])[2]")
	    private WebElement loginButton;
	 	
	 	@AndroidFindBy(xpath ="//android.view.View[@content-desc='My Activity']")
	    private WebElement myActivitytextinHomeScreen;
	 	
	 	 public LoginScreen(AppiumDriver driver) {
	 		 //PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	 		 this.driver=driver;
	 		genericMethods=new GenericMethods(driver);
	        PageFactory.initElements(driver, this);
	     }


	    public void clickonUIButton() throws Exception {
	    	genericMethods.waitForVisibilityOfElement(uiButton).click();
	    	Log.info("UI button was clicked");
	    }
	    
	    public void clickonNextbutton() throws Exception
	    {
	    	genericMethods.waitForVisibilityOfElement(nextButton).click();
	    	Log.info("Next button was clicked");
	    }
	    	    
	    public void clickonLetsMoveButton() throws Exception
	    {
	    	genericMethods.waitForVisibilityOfElement(letsMovebutton).click();
	    	Log.info("Lets Move button was clicked");
	    }
	    
	    public void enterEmail(String email) throws Exception
	    {
	    	genericMethods.waitForVisibilityOfElement(emailTextBox).click();
	    	genericMethods.waitForVisibilityOfElement(emailTextBox).sendKeys(email);
	    	Log.info("Email was entered");
	    }
	    
	    public void enterPassword(String Password) throws Exception
	    {
	    	genericMethods.waitForVisibilityOfElement(passwordTextBox).click();
	    	genericMethods.waitForVisibilityOfElement(passwordTextBox).sendKeys(Password);
	    	Log.info("Password was entered");
	    }
	    
	    public void clickonLoginButton() throws Exception
	    {
	    	genericMethods.waitForVisibilityOfElement(loginButton).click();
	    	Log.info("Login button was clicked");
	    }
	    
	    public Boolean ismyActivityDisplayed() throws Exception
	    {
	    	WebElement myactivity=genericMethods.waitForVisibilityOfElement(myActivitytextinHomeScreen);
	    	Log.info("myactivity text");
	    	if(myactivity.isDisplayed())
	    	{
	    		return true;
	    	}
	    	else 
	    	{
	    		return false;
	    	}
	    }







}
