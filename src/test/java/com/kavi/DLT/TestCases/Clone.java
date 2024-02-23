package com.kavi.DLT.TestCases;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.kavi.DLT.PageObjects.NovaPageObjects;
import com.kavi.DLT.Utilities.ReadConfig;
import com.kavi.DLT.Utilities.Take_Screenshot;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class Clone {
	
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}
	
	public WebDriver getDriver() {
		return this.driver.get();
	}
	
	public static WebDriverWait wait;
	ReadConfig RC = new ReadConfig();
	public String URL = RC.readfile();
	public static com.aventstack.extentreports.ExtentTest ExtentTest;
	
    private ExtentReports extent;
    private ExtentTest extentTest;
    @Parameters("browser")
    @BeforeClass
    public void setup(String br) throws Exception {	
    	if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\Data_Driven_Demo\\Driver\\chromedriver.exe");
			setDriver(new ChromeDriver());
			Thread.sleep(5000);

		}
		getDriver().get(URL);
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
		getDriver().manage().window().maximize();
		 ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/load-test-report.html");
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
    }

    @Test(invocationCount = 10, threadPoolSize = 1)
    public void LoginTest() throws Exception {
        extentTest = extent.createTest("Login Test - " + Thread.currentThread().getId(), "Description of the test");
        try {
        	getDriver().navigate().refresh();
        	try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"popup\"]")));

                NovaPageObjects novaPage = new NovaPageObjects(getDriver());
                novaPage.OpenDA();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[1]")));
                if (novaPage.getBotMessageTextWithText(RC.readOpenMsg())) {
                    novaPage.TextBox();
                    novaPage.Search("OMI-0085");
                    extentTest.info("User Name : " + "OMI-0085");
                    novaPage.Send();
                } else {
                    extentTest.info("The flow has been Disturbed. Please check the logs.");  
                    Assert.fail();
                }

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[2]")));
                if (novaPage.Text().contains(RC.readPasswordMsg())) {
                    novaPage.TextBox();
                    novaPage.Search("Omfys@123");
                    extentTest.info("Password : " + "Omfys@123");
                    novaPage.Send();
                } else {
                    extentTest.info("The flow has been Disturbed. Please check the logs.");
                    Assert.fail();
                }
                Thread.sleep(8000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[4]")));
                if (novaPage.HiUser().contains("Hi Pratik") && novaPage.WelcomeMsg().contains(RC.readWelcomeMsg())) {
                    Assert.assertTrue(true);
                    extentTest.info("Login Success.");
                    System.out.println("Login Success.");
                } else {
                	extentTest.info("Login Failed.");
                    System.out.println("Login Failed.");
                    extentTest.info("The flow has been Disturbed. Not able to Login.");
                    Assert.fail();
                }
Thread.sleep(3000);
                novaPage.Demolish();
                Thread.sleep(3000);
                novaPage.Yes();
                Thread.sleep(3000);
            } catch (Exception e) {
            	extentTest.info("Login Failed.");
                System.out.println("Login Failed.");
                extentTest.warning("Exception occurred during login test: " + e.getMessage());
                Assert.fail("Exception occurred during login test: " + e.getMessage());
            }
            
            extentTest.log(Status.PASS, "Test passed");
        } catch (Exception e) {
        	extentTest.info("Login Failed.");
            System.out.println("Login Failed.");
            extentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
    	getDriver().close();
    	extent.flush();
    }
}

