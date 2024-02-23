package com.kavi.DLT.TestCases;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.kavi.DLT.Utilities.ReadConfig;
import com.kavi.DLT.Utilities.Take_Screenshot;

public class BaseClass {

	// Webdriver driver
//	public static WebDriver driver;
	
	// Webdriver specifically for Parallel execution
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	// Method to select the driver
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}
	
	// Method to REPLACE the driver obj
	public WebDriver getDriver() {
		return this.driver.get();
	}
	
	public static WebDriverWait wait;

	// Read Properties File
	ReadConfig RC = new ReadConfig();
	public String URL = RC.readfile();
	
	//
	public static Take_Screenshot screenCapture;
	
	// object of Log4j
	protected static final Logger logger = LogManager.getLogger("BaseClass");
	// TO create report
	public static ExtentReports ExtentReports; 
	public static String Report_Sub_Folder;
	public static com.aventstack.extentreports.ExtentTest ExtentTest;
	
	@BeforeSuite
	public void initializeExtentReprts() {

		logger.info("Initializing Extent Reports with the specified configuration");

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH.mm.ss"); // String
																							// formattedDate=myDateObj.format(myFormatObj);
		Report_Sub_Folder = myDateObj.format(myFormatObj);

		ExtentReports = new ExtentReports();
		ExtentSparkReporter SparkReporter_Alltest = new ExtentSparkReporter(
				"Report\\Report-" + Report_Sub_Folder + "\\Data_Driven_Demo_Report.html");
		ExtentReports.attachReporter(SparkReporter_Alltest);
		SparkReporter_Alltest.config().setTheme(Theme.DARK);
		// to print the specific information in Report
		ExtentReports.setSystemInfo("Java_Version", System.getProperty("java.version"));
		ExtentReports.setSystemInfo("OS", System.getProperty("os.name"));

	}
	
	@Parameters("browser")
	@BeforeTest
	public void setUp(String br) throws Exception {

		logger.info("Setting up the WebDriver based on the Chrome browser");

		if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\Data_Driven_Demo\\Driver\\chromedriver.exe");
			setDriver(new ChromeDriver());
			Thread.sleep(5000);

		}
		getDriver().get(URL);
		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
		getDriver().manage().window().maximize();
	}
	
	@BeforeMethod
	public void initializeReport(ITestContext context) {

		ExtentTest = ExtentReports.createTest(context.getName());
		Capabilities capabilities = ((RemoteWebDriver) getDriver()).getCapabilities();

		String Device = capabilities.getBrowserName() + " " + capabilities.getBrowserVersion();
		ExtentTest.assignDevice(Device);
		String Author = context.getCurrentXmlTest().getParameter("Author");
		ExtentTest.assignAuthor(Author);
	}
	
	@AfterMethod
	public void After_TC_Failure(Method M, ITestResult result) throws Exception {
		screenCapture = new Take_Screenshot();

		if (result.getStatus() == ITestResult.FAILURE) {
			String path = result.getTestContext().getName() + " - " + result.getMethod().getMethodName();
			String ScreenshotPath = screenCapture
					.captureScreenshot(getDriver(), path);

			ExtentTest.addScreenCaptureFromPath(ScreenshotPath);
			Throwable Error_msg_of_Console = result.getThrowable();
			System.out.println("Error_msg_of_Console : " + Error_msg_of_Console);
			ExtentTest.fail(Error_msg_of_Console);

			logger.warn("Warning: Method Failed: " + result.getMethod().getMethodName());
			// logger.error("Error found: " + Error_msg_of_Console);
		} else if (result.getStatus() == ITestResult.SKIP) {
			System.out.println("Test Skipped: " + result.getMethod().getMethodName());
			ExtentTest.skip("Test Skipped: " + result.getMethod().getMethodName());

			logger.info("Test Skipped: " + result.getMethod().getMethodName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTest.pass(M.getName() + " method is Passed");

			logger.info("Test Case Method: " + result.getMethod().getMethodName() + " is Passed");
		}

//		 ExtentTest.assignCategory(M.getAnnotation(Test.class).groups());

	}
	
	@AfterTest
	public void Demolish() {
		logger.info("Closing the WebDriver after the test");

		getDriver().close();
	}
	
	@AfterSuite
	public void generateExtentReprts() {

		logger.info("Flushing the Extent Reports to generate the report"
				+ "\n------------------------------------------------------------------------------------------------------------------------------------");
		ExtentReports.flush();

//		Zip_Folder.Zp();
//
//		Send_Mail_with_Report.Mail();

	}

}
