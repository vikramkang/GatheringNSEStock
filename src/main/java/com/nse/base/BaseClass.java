package com.nse.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;




import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;



public class BaseClass {
	
	public static WebDriver driver;
	public static Properties prop;
	
	
	public BaseClass(){
		prop =new Properties();
		
		try {
		FileInputStream ip =new FileInputStream("C:\\Global ERP classes\\Project\\GatheringNSEStock\\src\\main\\java\\com\\nse\\config\\config.properties");
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void initialization(){
		String browser=prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "C://Tools//chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			
			driver =new ChromeDriver();}
		
		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C://Tools//geckodriver.exe");
			driver =new FirefoxDriver();
		}
			
		else {System.setProperty("webdriver.ie.driver", "C://Tools//iedriver.exe");
			driver =new InternetExplorerDriver();
		}
		driver.manage().window().maximize(); 
		driver.manage().deleteAllCookies();
		
		
	}
	public static Path captureScreenshot(WebDriver driver, String screenShotName) throws IOException{
		String dateName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File source=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/Screenshots/"+ screenShotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return finalDestination.toPath();
	}
	
	
	

}
