import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferPage extends BasePage {
    private By transferButton = By.xpath("//a[normalize-space()='Transfer Funds']");
    private By pageTitle = By.xpath("//h1[normalize-space()='Transfer Funds']");
    private By amountInput = By.id("amount");
    private By originAccount = By.id("fromAccountId");
    private By destinationAccount = By.id("toAccountId");
    private By confirmButton = By.xpath("//input[@value='Transfer']");
    private By confirmationMessage = By.xpath("//h1[normalize-space()='Transfer Complete!']");


    public TransferPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openTransferClick () throws InterruptedException{
        click(transferButton);
    }

    public String getPageTitle () throws InterruptedException {
        Thread.sleep(5000);
        return elementFind(pageTitle).getText();
    }

    public void transferFunds() throws InterruptedException {
        sendText("95", amountInput);
        Select origin = new Select(driver.findElement(originAccount));
        Select destination = new Select(driver.findElement(destinationAccount));
        origin.selectByValue("36654");
        destination.selectByValue("38097");
        click(confirmButton);
    }
    public String getConfirmationMessage () throws InterruptedException{
        Thread.sleep(4000);
        return elementFind(confirmationMessage).getText();
    }
}
