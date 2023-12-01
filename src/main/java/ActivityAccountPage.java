import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActivityAccountPage extends BasePage {
    private By overviewButton = By.xpath("//a[normalize-space()='Accounts Overview']");
    private By overviewMessage = By.xpath("//td[contains(text(),'*Balance includes deposits that may be subject to ')]");
    private By selectedAccount = By.xpath("//a[normalize-space()='36654']");
    private By periodSelect = By.id("month");
    private By transactionSelect = By.id("transactionType");
    private By goButton = By.xpath("//input[@value='Go']");

    public ActivityAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openOverviewClick () throws InterruptedException{
        click(overviewButton);
    }

    public String getOverviewMessage () throws InterruptedException{
        return elementFind(overviewMessage).getText();
    }
    public void enterAccount() throws InterruptedException {
        Thread.sleep(5000);
        click(selectedAccount);
    }

    public void selectActivity() throws InterruptedException {
        Thread.sleep(7000);
        Select period = new Select(driver.findElement(periodSelect));
        Select type = new Select(driver.findElement(transactionSelect));
        period.selectByValue("All");
        type.selectByValue("All");
        click(goButton);
    }
}
