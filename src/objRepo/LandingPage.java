package objRepo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import test.LandingPageScenario;

public class LandingPage extends LandingPageScenario{
	
	WebDriver driver;
	public LandingPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	    this.driver = driver;
	}
	
	@FindBy(xpath = "//a[@class='corp-header__logo']/img")
	public WebElement logo;
	
	@FindBy(className = "desktop-search__icon")
	public WebElement searchIcon;
	
	@FindBy(xpath = "//li[@class='desktop-search__search']/input")
	public WebElement searchIconTextField;
	
	@FindBy(xpath="//div[@class='navigation__title-holder']//a")
	public List<WebElement> navigationItem;
	
	@FindBy(xpath="(//li[@class='desktop-search__search']//a)[1]")
	public WebElement searchItem;
	
	@FindBy(xpath="//div[@id='at4-share']//a/span[2]")
	public List<WebElement> socialIcon;
	
	public String getTitle() throws InterruptedException {
		return driver.getTitle();
	}
	public boolean getLogo() throws InterruptedException {
		return logo.isDisplayed();
	}
	public String getNavigationItemText(WebElement item) throws InterruptedException {
		return item.getText();
	}
	public void clickOnSearchIcon(){
		searchIcon.click();
	}
	public void clickOnSocialMediaIcon(WebElement ele){
		ele.click();
	}
	public void clickOnSearchItem(){
		clickOnElement(searchItem);
	}
	public String GetCurrentURL(){
		return driver.getCurrentUrl();
	}
	public void EnterTextInSearchTextField(String searchText){
		sendKeysToElement(searchIconTextField, searchText);
		//searchIconTextField.sendKeys(searchText);
	}
	public void selectByText(String selectText, WebElement element) {
		Select sel = new Select(element);
		sel.selectByVisibleText(selectText);
	}
	
	
	public void clickByActions(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public void switchToFrame(String frameID) {
		driver.switchTo().frame(frameID);
	}
	
	public void clickOnElement(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.click();
	}
	
	public void sendKeysToElement(WebElement ele, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(ExpectedConditions.visibilityOf(ele));
		ele.sendKeys(text);
	}
	public String parentWin;
	public String childWin;
	
	public String switchToWindow(String window) {
		Set<String> wins = driver.getWindowHandles();
		Iterator<String> itr = wins.iterator();
		parentWin=itr.next();
		childWin=itr.next();
		if(window.equals("parent")) {
			return parentWin;	
		}else {		
			return childWin;
		}
	}
	
	
}

