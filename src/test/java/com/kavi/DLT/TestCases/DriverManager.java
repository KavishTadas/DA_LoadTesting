package com.kavi.DLT.TestCases;

import java.lang.reflect.Method;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.kavi.DLT.Utilities.ExtentReportListener;
import com.kavi.DLT.Utilities.ReadConfig;
import com.kavi.DLT.Utilities.Take_Screenshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass {

	// Webdriver specifically for Parallel execution
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public WebDriverWait wait;

	// Method to select the driver
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}

	// Method to REPLACE the driver obj
	public WebDriver getDriver() {
		return this.driver.get();
	}

	// Read Properties File
	ReadConfig RC = new ReadConfig();
	public String URL = RC.readfile();

	public static Take_Screenshot screenCapture;
	protected static final Logger logger = LogManager.getLogger("BaseClass");
	public static com.aventstack.extentreports.ExtentTest ExtentTest;
	public static ExtentReports ExtentReports;

	@BeforeTest
	@Parameters("browser")
	public void setUp(String browser) throws Exception {
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\Data_Driven_Demo\\Driver\\chromedriver.exe");
			setDriver(new ChromeDriver());
			Thread.sleep(5000);
		}
		getDriver().get(URL);
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
		getDriver().manage().window().maximize();
	}

	@BeforeMethod
	public void initializeReport(ITestContext context, Method method) {
		ExtentTest = ExtentReportListener.ExtentReports.createTest(context.getName() + "_" + method.getName());
		ExtentReportListener.ExtentTest.setTest(ExtentTest);
	}

	@AfterMethod
	public void After_TC_Failure(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			String path = result.getTestContext().getName() + " - " + result.getMethod().getMethodName();
			String screenshotPath = screenCapture.captureScreenshot(getDriver(), path);
			ExtentTest.fail(result.getThrowable());
			ExtentTest.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTest.skip("Test Skipped: " + result.getMethod().getMethodName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTest.pass("Test passed");
		}
	}

	@AfterTest
	public void Demolish() {
		getDriver().close();
	}
}
