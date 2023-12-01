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

public class ActivityAccountTest {
    private WebDriver driver;
    private WebDriverWait wait;
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTS/ActivityAccountTest.html");
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
        ActivityAccountPage activityAccountPage = new ActivityAccountPage(driver, wait);
        activityAccountPage.setUp();
        activityAccountPage.getUrl("https://parabank.parasoft.com/parabank/index.htm");
    }
    @Test
    @Tag("ActivityAccount")
    @Tag("ALL")
    public void successfulActivityOverview() throws InterruptedException {
        ExtentTest test = extent.createTest("Bank Account Activity Successful Test");
        test.log(Status.INFO, "Test - Begin");
        ActivityAccountPage activityAccountPage = new ActivityAccountPage(driver, wait);
        try {
            activityAccountPage.login("lolacatala", "lola4561");
            activityAccountPage.openOverviewClick();
            activityAccountPage.getOverviewMessage();
            Assertions.assertEquals(activityAccountPage.getOverviewMessage(), "*Balance includes deposits that may be subject to holds");
            test.log(Status.PASS, "Account overview successfully showed");
            activityAccountPage.enterAccount();
            activityAccountPage.selectActivity();

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
