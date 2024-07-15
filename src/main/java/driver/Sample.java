package driver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;;

public class Sample {
	
	//public static AndroidDriver driver= null;
	//public DesiredCapabilities capabilities=null;

	public static void main(String[] args) throws Exception 
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
	//	File fileReader=new File("E:\\Mahesh\\Automation\\workspace\\MoveMint\\src\\test\\resources\\app\\ApiDemos.apk");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "IQOO Z7 5G");
		capabilities.setCapability(MobileCapabilityType.UDID,"10BD5209JT0006G");
		
		capabilities.setCapability("appPackage", "com.codewave.raremint");
		capabilities.setCapability("appActivity", "com.codewave.raremint.MainActivity");
		//capabilities.setCapability("name", methodName.getName());

		//capabilities.setCapability("appium:usePlugins", "execute-driver");
		//capabilities.setCapability(MobileCapabilityType.APP,"");
		//capabilities.setCapability("noReset", true);
		
		AndroidDriver driver = new AndroidDriver( new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@content-desc='UI']")).click();
		Thread.sleep(2000);
		
	}
	

}
