import Reports.ExtentFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferTest {
    private WebDriver driver;
    private WebDriverWait wait;
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTS/TransferTest.html");
    static ExtentReports extent;

    @BeforeAll
    public static void createReport() {
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        TransferPage transferPage = new TransferPage(driver, wait);
        transferPage.setUp();
        transferPage.getUrl("https://parabank.parasoft.com/parabank/index.htm");
    }
    @Test
    @Tag("TransferFunds")
    @Tag("ALL")
    public void successfulTransfer() throws InterruptedException {
        ExtentTest test = extent.createTest("Bank Account Transfer Funds Successful Test");
        test.log(Status.INFO, "Test - Begin");
        TransferPage transferPage = new TransferPage(driver, wait);
        try {
            transferPage.login("lolacatala", "lola4561");
            transferPage.openTransferClick();
            transferPage.getPageTitle();
            Assertions.assertEquals(transferPage.getPageTitle(), "Transfer Funds");
            test.log(Status.PASS, "Transfer Funds title showed");
            transferPage.transferFunds();
            test.log(Status.PASS, "Successful transaction");
            Assertions.assertEquals(transferPage.getConfirmationMessage(), "Transfer Complete!");
            test.log(Status.PASS, "Successful message showed");

        } catch (AssertionError error) {
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }

    @AfterEach
    public void close() {
        RegisterPage registerPage = new RegisterPage(driver, wait);
        registerPage.close();
    }

    @AfterAll
    public static void getReport() {
        extent.flush();
    }
}
