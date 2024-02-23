package com.kavi.DLT.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.kavi.DLT.TestCases.BaseClass;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ExtentReportListener extends TestListenerAdapter  {

	public static final String ExtentTest = null;
	private ExtentReports extent;
	private ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
	private ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		extent = ExtentManager.createInstance("extent-report.html");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest parent = extent.createTest(result.getMethod().getMethodName());
		parentTest.set(parent);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		parentTest.get().pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		parentTest.get().fail(result.getThrowable());
		Take_Screenshot captureScreen = new Take_Screenshot();
		captureScreen.captureScreenshot(BaseClass.getDriver(),(result.getMethod().getMethodName());
	}

//	private void captureScreenshot(String testName) throws Exception {
//		WebDriver driver = BaseClass.getDriver(); // Assuming you have a driver manager class
//		if (driver instanceof TakesScreenshot) {
//			TakesScreenshot ts = (TakesScreenshot) driver;
//			File srcFile = ts.getScreenshotAs(OutputType.FILE);
//			try {
//				String screenshotPath = "screenshots/" + testName + ".png";
//				FileUtils.copyFile(srcFile, new File(screenshotPath));
//				parentTest.get().fail("Screenshot",
//						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
