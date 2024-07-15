package tests;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import driver.CreateSession;
import logger.Log;
import screens.LoginScreen;
import utilites.GenericMethods;

public class LoginTest extends CreateSession{
	
	LoginScreen ls;
	//public AppiumDriver driver;
	public GenericMethods genericMethods;
	//private static final Logger logger = (Logger) LogManager.getLogger(LoginTest.class);
	
	/** 
	 * this method instantiate required helpers depending on the platform(android or iOS)
	 * @param invokeDriver android or iOS
	 * @throws Exception 
	 */
	@BeforeTest
	public void intiate() throws Exception{
			//invokeAppium();
			//String androidAppPath = localeConfigProp.getProperty("andriodpath");
			androidDriver();
			ls=new LoginScreen(driver);	
			//ls = PageFactory.initElements(driver, LoginScreen.class);
			genericMethods=new GenericMethods(driver);
	}
	
	@Test
	public void validlogin() throws Exception
	{
		try{
			System.out.println("hello");
			ls.clickonUIButton();
			ls.clickonNextbutton();
			ls.clickonNextbutton();
			ls.clickonLetsMoveButton();
			ls.enterEmail(getPropertyValue("ValidEmail"));
			ls.enterPassword(getPropertyValue("ValidPassword"));
			driver.navigate().back();
			ls.clickonLoginButton();
			Thread.sleep(5000);
			if(ls.ismyActivityDisplayed()){
				Log.info("User Successfully Login");
				Assert.assertTrue(true);
			}else{
				String fp=genericMethods.captureScreenShot();
    			Reporter.log("<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a>");
				Log.logError(this.getClass().getName(), "User unable login","...");
				Assert.assertTrue(false);
			}
		}catch(Exception ex){
			System.out.println("Exception is "+ex.getMessage());
			genericMethods.captureScreenShot();
			Log.logError(this.getClass().getName(), "User unable login due to ",ex.getMessage());

			Assert.assertTrue(false);
		}
	}
	
	

}
