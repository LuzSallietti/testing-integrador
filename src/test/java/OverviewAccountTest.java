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

public class OverviewAccountTest {
    private WebDriver driver;
    private WebDriverWait wait;
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTS/OverviewAccountTest.html");
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
        OverviewAccountPage overviewAccountPage = new OverviewAccountPage(driver, wait);
        overviewAccountPage.setUp();
        overviewAccountPage.getUrl("https://parabank.parasoft.com/parabank/index.htm");
    }
    @Test
    @Tag("OverviewAccount")
    @Tag("ALL")
    public void successfulOverview() throws InterruptedException {
        ExtentTest test = extent.createTest("Bank Account Overview Successful Test");
        test.log(Status.INFO, "Test - Begin");
        OverviewAccountPage overviewAccountPage = new OverviewAccountPage(driver, wait);
        try {
            overviewAccountPage.login("lolacatala", "lola4561");
            overviewAccountPage.openOverviewClick();
            test.log(Status.INFO, "Login data sent");
            overviewAccountPage.getOverviewMessage();
            Assertions.assertEquals(overviewAccountPage.getOverviewMessage(), "*Balance includes deposits that may be subject to holds");
            test.log(Status.PASS, "Account overview successfully showed");
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
