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

public class OpenAccountTest {
    private WebDriver driver;
    private WebDriverWait wait;
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTS/OpenAccountTest.html");
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
        RegisterPage registerPage = new RegisterPage(driver, wait);
        registerPage.setUp();
        registerPage.getUrl("https://parabank.parasoft.com/parabank/index.htm");
    }
    @Test
    @Tag("OpenAccount")
    @Tag("ALL")
    public void successfulAccountOpening() throws InterruptedException {
        ExtentTest test = extent.createTest("Bank Savings Account Succesful Creation Test");
        test.log(Status.INFO, "Test - Begin");
        OpenAccountPage openAccountPage = new OpenAccountPage(driver, wait);
        try {
            openAccountPage.login("lolacatala", "lola4561");
            openAccountPage.openAccountClick();
            test.log(Status.PASS, "Login data succesfully completed");
            openAccountPage.selectSavingsType();
            test.log(Status.PASS, "Savings type selected");
            openAccountPage.confirmOpening();
            openAccountPage.getConfirmation();
            Assertions.assertEquals(openAccountPage.getConfirmation(), "Congratulations, your account is now open.");

        } catch (AssertionError error) {
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }

    /*@AfterEach
    public void close() {
        RegisterPage registerPage = new RegisterPage(driver, wait);
        registerPage.close();
    }*/

    @AfterAll
    public static void getReport() {
        extent.flush();
    }
}
