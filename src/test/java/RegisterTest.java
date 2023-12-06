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

public class RegisterTest {
    private WebDriver driver;
    private WebDriverWait wait;
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTS/RegisterTest.html");
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
    @Tag("Register")
    @Tag("ALL")
    public void successfulRegister() throws InterruptedException {
        ExtentTest test = extent.createTest("User Account Succesful Creation Test");
        test.log(Status.INFO, "Test - Begin");
        RegisterPage registerPage = new RegisterPage(driver, wait);
        try {
            registerPage.registerClick();
            Assertions.assertEquals(registerPage.getRegisterTitle(), "Signing up is easy!");
            test.log(Status.PASS, "Access to the Registration page");
            Assertions.assertEquals(registerPage.getRegisterForm(), "form");
            test.log(Status.PASS, "Form present on Registration page");
            registerPage.insertFirstName("Lola");
            registerPage.insertLastName("Catala");
            registerPage.insertStreet("6176 Enim Street");
            registerPage.insertCity("Birmingham");
            registerPage.insertState("Alabama");
            registerPage.insertZipCode("35004");
            registerPage.insertPhone("3512258789");
            registerPage.insertSsn("416-11-XXXX");
            registerPage.insertUsername("lolacatala");
            registerPage.insertPassword("lola4561");
            registerPage.confirmPassword("lola4561");
            test.log(Status.PASS, "Registration data succesfully completed");
            registerPage.confirmRegister();
            Assertions.assertEquals(registerPage.getConfirmation(), "Your account was created successfully. You are now logged in.");

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
