package com.kavi.DLT.TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.kavi.DLT.PageObjects.NovaPageObjects;

public class DA_LoginTest2 extends BaseClass {

	@Test(invocationCount = 1, threadPoolSize = 1)
	public void LoginTest2() throws Exception {

		try {
			getDriver().navigate().refresh();
			Thread.sleep(1000);
			try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"popup\"]")));

			NovaPageObjects novaPage = new NovaPageObjects(getDriver());
			novaPage.OpenDA();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[1]")));
			if (novaPage.getBotMessageTextWithText(RC.readOpenMsg())) {
				novaPage.TextBox();
				novaPage.Search("OMI-0001");
				ExtentTest.info("User Name : " + "OMI-0001");
				novaPage.Send();
			} else {
				ExtentTest.info("The flow has been disturbed. Please check the logs.");
				ExtentTest.info("Employee Id not sent.");
				Assert.fail();
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[2]")));
			if (novaPage.Text().contains(RC.readPasswordMsg())) {
				novaPage.TextBox();
				novaPage.Search("Omfys@123");
				ExtentTest.info("Password : " + "Omfys@123");
				novaPage.Send();
			} else {
				ExtentTest.info("The flow has been disturbed. Please check the logs.");
				ExtentTest.info("Password not sent.");
				Assert.fail();
			}
			Thread.sleep(8000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='botMsg'])[4]")));
			if (novaPage.HiUser().contains("Hi Babu") && novaPage.WelcomeMsg().contains(RC.readWelcomeMsg())) {
				Assert.assertTrue(true);
				ExtentTest.info("Login Sucess.");
				System.out.println("Login Sucess.");
			} else {
				ExtentTest.warning("Login Failed.");
				System.out.println("Login Failed.");
				ExtentTest.warning("The flow has been disturbed. Please check the logs.");
				ExtentTest.warning("DA is not responding.");
				Assert.fail();
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@id=\"closeb\"]")));
			novaPage.Demolish();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id=\"Ybut\"]")));
			novaPage.Yes();
			} catch (Exception e) {
				ExtentTest.warning("Login Failed.");
				System.out.println("Login Failed.");
				ExtentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
				Assert.fail("Test failed: " + e.getMessage());
			}
		} catch (Exception e) {
			ExtentTest.warning("Login Failed.");
			System.out.println("Login Failed.");
			ExtentTest.log(Status.FAIL, "Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		}

	}

}
