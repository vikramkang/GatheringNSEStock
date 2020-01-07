package com.nse.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nse.base.BaseClass;


public class NSEMainPage extends BaseClass{
	
	
	
	
	@FindBy(xpath="//span[contains(text(), 'Underlying')]")
	WebElement underLyingIndex;
	
	@FindBy(xpath="//table[@id='octable']//thead//tr[2]//th")
	List<WebElement> tableLinks;
	
	
	
	
	
	public NSEMainPage(){
		PageFactory.initElements(driver, this);
	}
	

	
	
	public float getNiftyUnderLyingIndex(){
		float niftyUnderlyingIndex=Float.parseFloat(underLyingIndex.getText().split(" ")[underLyingIndex.getText().split(" ").length-1]);
		return niftyUnderlyingIndex;
		
	}
	
	public List<WebElement> getTableLinks(){
		return tableLinks;
	}
	
	public int getStrikePriceIndex(List<WebElement>tableLinks){
		int strikePriceIndex = 0;
	for (int i = 0; i < tableLinks.size(); i++) {
		if (tableLinks.get(i).getText().equalsIgnoreCase("Strike Price")) {
			 strikePriceIndex=i+1;	
		}}return strikePriceIndex;}
	
	public int getCallsLTPIndex(List<WebElement>tableLinks){
		int flag=0, callsLTPIndex=0;
		for (int i = 0; i < tableLinks.size(); i++) {
		if (tableLinks.get(i).getText().equalsIgnoreCase("LTP")&& (flag==0)) {
			callsLTPIndex=i+1;
			flag=1;
		}}return callsLTPIndex;}
		
	public int getPutLTPIndex(List<WebElement>tableLinks){
		int putLTPIndex=0;
		for (int i = 0; i < tableLinks.size(); i++) {
		if (tableLinks.get(i).getText().equalsIgnoreCase("LTP")) {
			putLTPIndex=i+1;	
		}}return putLTPIndex;}
	
	public List<WebElement> getStrikePriceLinks(int strikePriceIndex ){
		
		List<WebElement> strikePriceLinks=	driver.findElements(By.xpath("//tbody//tr//td["+strikePriceIndex+"]"));
		return strikePriceLinks;
		}
	
	public String[] webElementsToString(List<WebElement> strikePriceLinks){
		String[] strikePriceLinksText =new String[strikePriceLinks.size()];
		int i=0;

		//Storing List elements text into String array
		for(WebElement a: strikePriceLinks)
		{
			strikePriceLinksText[i]=(a.getText());
		   i++;
		}
		return strikePriceLinksText;
	}
	
	
	public  int getClosestToTargetIndex(String[] strikePriceLinksText, float niftyUnderlyingIndex) {
		
		int closestToTargetIndex=0;
	    if (strikePriceLinksText.length < 1)
	        throw new IllegalArgumentException("The values should be at least one element");
	    
	    if (strikePriceLinksText.length == 1) {
	        return closestToTargetIndex;
	    }
	    //float closestValue = Float.parseFloat(StringPriceLinksText[0]);
	    float leastDistance = Math.abs(Float.parseFloat(strikePriceLinksText[0]) - niftyUnderlyingIndex);
	    for (int k = 0; k < strikePriceLinksText.length; k++) {
	        float currentDistance = Math.abs(Float.parseFloat(strikePriceLinksText[k]) - niftyUnderlyingIndex);
	        if (currentDistance < leastDistance) {
	            //closestValue = Float.parseFloat(StringPriceLinksText[k]);
	            leastDistance = currentDistance;
	           closestToTargetIndex=k;
	        }
	    }
	    return closestToTargetIndex;
	}
	
	public float getStrikePriceValue(String[] strikePriceLinksText, int closestToTargetIndex ){
		float strikePriceValue=Float.parseFloat(strikePriceLinksText[closestToTargetIndex]);
	return strikePriceValue;
	
	}
	
	public float getCallLTPValue(int callsLTPIndex,  int closestToTargetIndex){
		List<WebElement> callLTPValues=driver.findElements(By.xpath("//tbody//tr//td["+callsLTPIndex+"]"));
		float callLTPValue = Float.parseFloat(webElementsToString(callLTPValues)[closestToTargetIndex]);
		return callLTPValue;
		
	}
	
	
	public float getPutLTPValue(int putLTPIndex,  int closestToTargetIndex){
		List<WebElement> putLTPValues=driver.findElements(By.xpath("//tbody//tr//td["+putLTPIndex+"]"));
		float putLTPValue = Float.parseFloat(webElementsToString(putLTPValues)[closestToTargetIndex]);
		return putLTPValue;
	}
	
	
	
	
	
	

}
