package com.kavi.DLT.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NovaPageObjects {
	
	private final WebDriver driver;

    // Constructor to initialize the WebDriver and PageFactory
    public NovaPageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
 // Locators for elements
    @CacheLookup
    @FindBy(xpath = "//div[@id=\"profile_div\"]")
    private WebElement OpenDA;
    
    @CacheLookup
	@FindBy(xpath = "//input[@id=\"keypad\"]")
	private WebElement Search;
    
    @CacheLookup
    @FindBy(xpath = "(//p[@class='botMsg'])")
    private List<WebElement> botMessages;
    
    @CacheLookup
    @FindBy(xpath = "(//i[@class=\"material-icons\"])[5]")
    private WebElement Send;
    
//    ("//p[@class='botMsg' and contains(text(), 'Hey! I am NOVA. Your HR Digital Assistance. Could you please provide me login ID?')]")
    
 // Methods to interact with elements
    public void OpenDA() {
		OpenDA.click();
    }

	public void TextBox() {
		Search.click();
	}
	
	public void Search(String Input) {
		Search.sendKeys(Input);
	
	}
	
	public String getBotMessageTextAtIndex(int index) {
        if (index >= 0 && index < botMessages.size()) {
            return botMessages.get(index).getText();
        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }
	
	public Boolean getBotMessageTextWithText(String messageText) {
        for (WebElement messageElement : botMessages) {
            if (messageElement.getText().equals(messageText)) {
                return true;
            }
        }
        return null; // Return null if the message with the specific text is not found
    }

	
	public void Send() {
		Send.click();
	}

	@CacheLookup
    @FindBy(xpath = "(//p[@class='botMsg'])[2]")
    private WebElement botMsg;
    public String Text() {
    	return botMsg.getText();
    }
    
    @CacheLookup
    @FindBy(xpath = "(//p[@class='botMsg'])[4]")
    private WebElement WlcMsg;
    public String WelcomeMsg() {
	    return WlcMsg.getText();
    }
    
    @CacheLookup
    @FindBy(xpath = "(//p[@class='botMsg'])[3]")
    private WebElement HIMsg;
    public String HiUser() {
	    return HIMsg.getText();
    }
	
    @CacheLookup
	@FindBy(xpath = "//i[@id=\"closeb\"]")
	private WebElement Close;
    public void Demolish() {
	    Close.click();
    }
    
    @CacheLookup
	@FindBy(xpath = "//a[@id=\"Ybut\"]")
	private WebElement Yes;
	public void Yes() {
		Yes.click();
	}
    
}
