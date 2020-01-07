package com.nse.test;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nse.pages.NSEMainPage;
import com.nse.util.TestUtil;
import com.nse.base.BaseClass;

public class NSEMainTest extends BaseClass{
	TestUtil testUtil;
	NSEMainPage nSEMainPage;
	
	
	
	
	@BeforeMethod
	public void setUp(){
		initialization();
		
	}
	
	@DataProvider
	public Object[][] readTestData() throws InvalidFormatException, IOException{
		testUtil=new TestUtil();
		Object data[][]=testUtil.getTestData(prop.getProperty("sheetName"));
		return data;
		}
		
	@Test(dataProvider="readTestData")
	public void callValuesTest(String symbol) throws InterruptedException, IOException{
		testUtil=new TestUtil();
		nSEMainPage =new NSEMainPage();
		String url=prop.getProperty("url")+"?symbol="+symbol;
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
		
		float niftyUnderlyingIndex=nSEMainPage.getNiftyUnderLyingIndex();
		
		List<WebElement>tableLinks=nSEMainPage.getTableLinks();
		
		int strikePriceIndex=nSEMainPage.getStrikePriceIndex(tableLinks);
		
		int callsLTPIndex=nSEMainPage.getCallsLTPIndex(tableLinks);
		int putLTPIndex =nSEMainPage.getPutLTPIndex(tableLinks);
		
		List<WebElement> strikePriceLinks=nSEMainPage.getStrikePriceLinks(strikePriceIndex);
		String[] strikePriceLinksText= nSEMainPage.webElementsToString(strikePriceLinks);
		
		int closestToTargetIndex=nSEMainPage.getClosestToTargetIndex(strikePriceLinksText, niftyUnderlyingIndex);
		float strikePriceValue=nSEMainPage.getStrikePriceValue(strikePriceLinksText, closestToTargetIndex);
		float callLTPvalue= nSEMainPage.getCallLTPValue(callsLTPIndex, closestToTargetIndex);
		float putLTPvalue=nSEMainPage.getPutLTPValue(putLTPIndex, closestToTargetIndex);
		float sumCallAndPut=callLTPvalue+putLTPvalue;
		float strikePercent=(sumCallAndPut/strikePriceValue)*100;
		String outPut=symbol +","+niftyUnderlyingIndex +","+strikePriceValue+
				","+ callLTPvalue+ ","+ putLTPvalue+
				"," + sumCallAndPut+","+ strikePercent;
		testUtil.writeTextFile(outPut);
		
		testUtil.closeTextFile();
		
		
		
		
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	

	


}