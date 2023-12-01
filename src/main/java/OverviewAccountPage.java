import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OverviewAccountPage extends BasePage{
    private By overviewButton = By.xpath("//a[normalize-space()='Accounts Overview']");
    private By overviewMessage = By.xpath("//td[contains(text(),'*Balance includes deposits that may be subject to ')]");

    public OverviewAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openOverviewClick () throws InterruptedException{
        click(overviewButton);
    }

    public String getOverviewMessage () throws InterruptedException{
        return elementFind(overviewMessage).getText();
    }
}
