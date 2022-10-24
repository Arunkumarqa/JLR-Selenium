package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import objRepo.LandingPage;

public class LandingPageScenario {
	
	WebDriver driver;
	FileInputStream file;	
	public static Properties prop;
	LandingPage landPage;
	protected static WebDriverWait wait;
	private static ExtentReports report; 
	private static ExtentTest logger; 
	String actualTitle;
	String expectedTitle;
	
	@BeforeSuite
	public void baseDriver() throws IOException {
	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		file = new FileInputStream(System.getProperty("user.dir")+"\\src\\donarData.Properties");
        prop = new Properties();
        prop.load(file);
      
        landPage = new LandingPage(driver);
        wait = new WebDriverWait(driver, 20);
        report = new ExtentReports(System.getProperty("user.dir") + "\\result.html");
		logger = report.startTest("Opening the Browser");
	}	
	
	@BeforeMethod
	public void openURL() {
		driver.get(prop.getProperty("baseURL"));
		logger.log(LogStatus.INFO, "Navigated to JLR landing page");
	}
	
	@Test(priority = 0)    
	public void verifyPageLoad() throws InterruptedException {
		try {
		logger = report.startTest("Starting the test for JLR landing page URL");
		actualTitle = landPage.getTitle();
		expectedTitle = prop.getProperty("Title");
		Assert.assertEquals(actualTitle,expectedTitle);
		logger.log(LogStatus.INFO, "Verified the page load");
		logger.log(LogStatus.PASS, "Actual("+actualTitle+") and Expected("+expectedTitle+") Title are equal");
		}
		catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Actual("+actualTitle+") and Expected("+expectedTitle+") Title are not equal");
			Assert.fail();
		}
	}
	
	@Test(priority = 1)    
	public void verifyLogo() throws InterruptedException {
		try {
		logger = report.startTest("Starting the test for Verifying the JLR landing page Logo");
		
		Assert.assertEquals(landPage.getLogo(),true);
		logger.log(LogStatus.INFO, "Verified the Landing page Logo");
		logger.log(LogStatus.PASS, "Logo is displayed in the landing page");
		}
		catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Logo is not displayed in the landing page");
			Assert.fail();
		}
	}
	
	@Test(priority = 2)    
	public void verifyNavigationItem() throws InterruptedException {
		int count = 1;
		try {
		logger = report.startTest("Starting the test for Verifying the JLR Header navigation item");
		for(WebElement item:landPage.navigationItem) {
			String expectedItem= "HeaderNavigationItem$".replace("$", String.valueOf(count));
			Assert.assertEquals(landPage.getNavigationItemText(item), prop.getProperty(expectedItem));
			count++;
		}
		logger.log(LogStatus.INFO, "Verified the Landing page Navigation Items");
		logger.log(LogStatus.PASS, "Navigations Items are displayed in the landing page");
		}
		catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Navigations Items are not displayed in the landing page");
			Assert.fail();
		}
	}
	
	@Test(priority = 3)    
	public void verifySearchIcon() {
		try {
		logger = report.startTest("Starting the test for JLR landing page Search Icon");
		landPage.clickOnSearchIcon();
		landPage.EnterTextInSearchTextField(prop.getProperty("searchText"));
		landPage.clickOnSearchItem();
		boolean IsNavigatedsearchUrl = landPage.GetCurrentURL().contains("search");
		Assert.assertEquals(IsNavigatedsearchUrl, true);
		
		logger.log(LogStatus.INFO, "Verified the Search components");
		logger.log(LogStatus.PASS, "Page Navigated to Search page after click on the item in search list");
		}
		catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Page does not Navigated to Search page after click on the item in search list");
			Assert.fail();
		}
	}
	
	@Test(priority = 4)    
	public void verifySocialMediaComponents() {
		try {
			int count = 1;
		logger = report.startTest("Starting the test for JLR landing page Social Media Components");
		for(int i=0;i<2;i++) {
			landPage.clickOnSocialMediaIcon(landPage.socialIcon.get(i));
			driver.switchTo().window(landPage.switchToWindow("child"));
			String expectedItem= "socialMedia$".replace("$", String.valueOf(count));
			boolean IsNavigated = landPage.GetCurrentURL().contains(prop.getProperty(expectedItem));
			driver.close();
			driver.switchTo().window(landPage.parentWin);
			Assert.assertEquals(IsNavigated, true);
			count++;
		}
		
		
		
		logger.log(LogStatus.INFO, "Verified the Social media components");
		logger.log(LogStatus.PASS, "Page Navigated to Search page after click on the item in search list");
		}
		catch(AssertionError e) {
			logger.log(LogStatus.FAIL, "Page does not Navigated to Search page after click on the item in search list");
			Assert.fail();
		}
	}
	
	
	@AfterSuite	
	public void closeDriver() {	
		report.endTest(logger);
		report.flush();
		driver.get(System.getProperty("user.dir")+"\\result.html");
	}

}
