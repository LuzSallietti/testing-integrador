import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenAccountPage extends BasePage {
    private By openButton = By.xpath("//a[normalize-space()='Open New Account']");
    private By accountTypeSelect = By.xpath("//select[@id='type']");
    private By savingOption = By.xpath("//option[@value='1']");
    private By confirmButton = By.xpath("//input[@value='Open New Account']");
    private By congratulationsMessage = By.xpath("//p[normalize-space()='Congratulations, your account is now open.']");

    public OpenAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openAccountClick () throws InterruptedException{
        click(openButton);
    }

    public void selectSavingsType () throws InterruptedException {
        Select select = new Select(driver.findElement(accountTypeSelect));
        select.selectByValue("1"); // o usar select.selectByVisibleText("SAVINGS")
    }

    public void confirmOpening () throws InterruptedException {
        click(confirmButton);
    }
    public String getConfirmation () throws InterruptedException{
        //Thread.sleep(5000);
        return elementFind(congratulationsMessage).getText();
    }
}
