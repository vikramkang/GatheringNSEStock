package com.nse.pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NSEDataExtract {
	
	public static WebDriver driver;
	public static float stringPriceIndex;
	public static int callsLTPIndex;
	public static int flag=0;
	public static String[] stringPriceLinksText;
	public static String[] callLTPValuesText;
	public static String[] putLTPValuesText;
	public static float niftyUnderlyingIndex;
	public static int putLTPIndex;
	public static List<WebElement> StringPriceLinks;
	public static void main(String[] args){
		System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://www1.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp");
		driver.findElement(By.xpath("//input[contains(@value, 'Search for an underlying stock:')]")).sendKeys("IDEA");
		WebElement goBtn=driver.findElement(By.xpath("//input[contains(@onclick, 'goBtnClick')]"));
		
		clickElementByJS(goBtn, driver);
		

		
		String underlyingIndex=driver.findElement(By.xpath("//span[contains(text(), 'Underlying')]")).getText();
		niftyUnderlyingIndex=Float.parseFloat(underlyingIndex.split(" ")[underlyingIndex.split(" ").length-1]);
		List<WebElement> tableLinks=driver.findElements(By.xpath("//table[@id='octable']//thead//tr[2]//th"));
		for (int i = 0; i < tableLinks.size(); i++) {
			if (tableLinks.get(i).getText().equalsIgnoreCase("Strike Price")) {
				stringPriceIndex=i+1;	
			}
			if (tableLinks.get(i).getText().equalsIgnoreCase("LTP")&& (flag==0)) {
				callsLTPIndex=i+1;
				flag=1;
			}
			if (tableLinks.get(i).getText().equalsIgnoreCase("LTP")) {
				putLTPIndex=i+1;	
			}
			
		}
		
		StringPriceLinks=driver.findElements(By.xpath("//tbody//tr//td["+stringPriceIndex+"]"));
		
		float stringPriceValue=Float.parseFloat(webElementsToString(StringPriceLinks)[getClosestToTargetIndex()]);
		List<WebElement> callLTPValues=driver.findElements(By.xpath("//tbody//tr//td["+callsLTPIndex+"]"));
		float callLTPValue = Float.parseFloat(webElementsToString(callLTPValues)[getClosestToTargetIndex()]);
		
		List<WebElement> putLTPValues=driver.findElements(By.xpath("//tbody//tr//td["+putLTPIndex+"]"));
		float putLTPValue = Float.parseFloat(webElementsToString(putLTPValues)[getClosestToTargetIndex()]);
		
			
		System.out.println("Nifty Underlying Index= "+ niftyUnderlyingIndex);
		System.out.println(("The closest String Price value= "+ stringPriceValue));
		System.out.println(("The closest call value= "+ callLTPValue));
		System.out.println(("The closest put value is= "+putLTPValue));
		
		System.out.println("The Sum of call and put values= " +(callLTPValue+putLTPValue));
		driver.quit();
		
		
	}

	public static String[] webElementsToString(List<WebElement> links){
		String []linkText =new String[links.size()];
		int i=0;

		//Storing List elements text into String array
		for(WebElement a: links)
		{
		   linkText[i]=(a.getText());
		   i++;
		}
		return linkText;
	}
		
	public static int getClosestToTargetIndex() {
		stringPriceLinksText=webElementsToString(StringPriceLinks);
		int i=0;
	    if (stringPriceLinksText.length < 1)
	        throw new IllegalArgumentException("The values should be at least one element");
	    
	    if (stringPriceLinksText.length == 1) {
	        return i;
	    }
	    //float closestValue = Float.parseFloat(StringPriceLinksText[0]);
	    float leastDistance = Math.abs(Float.parseFloat(stringPriceLinksText[0]) - niftyUnderlyingIndex);
	    for (int k = 0; k < stringPriceLinksText.length; k++) {
	        float currentDistance = Math.abs(Float.parseFloat(stringPriceLinksText[k]) - niftyUnderlyingIndex);
	        if (currentDistance < leastDistance) {
	            //closestValue = Float.parseFloat(StringPriceLinksText[k]);
	            leastDistance = currentDistance;
	            i=k;
	        }
	    }
	    return i;
	}
	
	// method to click on Element
			public static void clickElementByJS(WebElement element, WebDriver driver){
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("arguments[0].click();", element);
			}
			
				
	
	}
