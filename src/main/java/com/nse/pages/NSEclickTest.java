package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NSEclickTest {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		// Instantiate the chrome driver
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www1.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[contains(@value, 'Search for an underlying stock:')]")).sendKeys("IDEA"+Keys.ENTER);
		//Thread.sleep(4000);
		//WebElement goBtn=driver.findElement(By.xpath("//input[contains(@onclick, 'goBtnClick')]"));
		
		//  once elements is ready it will clicked
		
		//goBtn.sendKeys(Keys.ENTER);
		/*Actions actions = new Actions(driver);
		Thread.sleep(2000);
		actions.moveToElement(goBtn).click().build().perform();
		//JavascriptExecutor js =(JavascriptExecutor) driver; 
*/			//js.executeScript("arguments[0].click();",goBtn);
		
			
		
	}

}
