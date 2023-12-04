package BackTests;

import Reports.ExtentFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class BackendTests {
    static ExtentSparkReporter info = new ExtentSparkReporter("target/BACK-REPORTS/EndpointsTests.html");
    static ExtentReports extent;
    // Método para realizar el login y obtener el Customer ID
    private int loginAndGetCustomerId() {
        Response resGetUserId = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/login/lolacatala/lola4561");
        Assertions.assertEquals(resGetUserId.statusCode(), 200);

        XmlPath xmlPath = resGetUserId.xmlPath();
        return xmlPath.getInt("customer.id");
    }

    @BeforeAll
    public static void crearReporte() {
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }
    @Test
    @Tag("GET")
    public void registerAccount (){
        ExtentTest test = extent.createTest("Register Account Endpoint - BACK END - Successful Test");
        test.log(Status.INFO, "Tests-Begin");

        try {
            Response resGetRegister = RestAssured.post("https://parabank.parasoft.com/parabank/register.htm");

            Assertions.assertEquals(resGetRegister.statusCode(), 200);
            test.log(Status.PASS, "Register Account Service/Endpoint available - Status code 200 received");
        } catch (AssertionError error){
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }
    @Test
    @Tag("POST")
    public void openSavingsAccount () {
        ExtentTest test = extent.createTest("Open Savings Account - BACK END - Successful Test");
        test.log(Status.INFO, "Tests-Begin");
        try {
            int customerId = loginAndGetCustomerId();
            test.log(Status.PASS, "Customer id succesfully received");

            //Usar el Customer ID en la solicitud de ID de Account ID
            test.log(Status.INFO, "OPEN SAVINGS ACCOUNT Test - Begin");
            test.log(Status.INFO, "Getting Account ID by Customer ID");
            Response resGetAccountId = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customerId + "/accounts");
            XmlPath xmlPath1 = resGetAccountId.xmlPath();
            int accountId = xmlPath1.getInt("accounts.account[0].id");
            Assertions.assertEquals(resGetAccountId.statusCode(), 200);
            System.out.println("Account ID: " + accountId);
            test.log(Status.PASS, "Account id by customer succesfully received");

            // Usar el Customer ID y la Account ID en la siguiente solicitud
            Response resCreateAccount = RestAssured.post("https://parabank.parasoft.com/parabank/services/bank/createAccount?customerId=" + customerId + "&newAccountType=1&fromAccountId=" + accountId);

            Assertions.assertEquals(resCreateAccount.statusCode(), 200);
            test.log(Status.PASS, "Savings Account CREATED - Status code 200 received");

        } catch (AssertionError error) {
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }
    @Test
    @Tag("GET")
    public void accountOverview (){
        ExtentTest test = extent.createTest("Account Overview - BACK END - Successful Test");
        test.log(Status.INFO, "Tests-Begin");
        try {
            int customerId = loginAndGetCustomerId();
            test.log(Status.PASS, "Customer id successfully received");
            //Usar el Customer ID en la solicitud de overview
            test.log(Status.INFO, "ACCOUNT OVERVIEW Test - Begin");
            test.log(Status.INFO, "Getting AccountS by Customer ID");
            Response resGetAccountId = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customerId + "/accounts");
            Assertions.assertEquals(resGetAccountId.statusCode(), 200);
            test.log(Status.PASS, "AccountS Overview  by customer id succesfully received");
        } catch (AssertionError error) {
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }
    @Test
    @Tag("GET")
    public void accountActivity () {
        ExtentTest test = extent.createTest("Account Activity - BACK END - Successful Test");
        test.log(Status.INFO, "Tests-Begin");
        try {
            int customerId = loginAndGetCustomerId();
            test.log(Status.PASS, "Customer id successfully received");
            //Usar el Customer ID para obtener una account id
            test.log(Status.INFO, "ACCOUNT ACTIVITY Test - Begin");
            test.log(Status.INFO, "Getting accounts by Customer ID");
            Response resGetAccountId = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customerId + "/accounts");
            XmlPath xmlPath1 = resGetAccountId.xmlPath();
            int accountId = xmlPath1.getInt("accounts.account[0].id");
            Assertions.assertEquals(resGetAccountId.statusCode(), 200);
            test.log(Status.PASS, "Account ID received");
            test.log(Status.INFO, "Request Account Activity");
            Response resGetAccountActivity = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/accounts/"+accountId+"/transactions/month/All/type/All");
            Assertions.assertEquals(resGetAccountActivity.statusCode(), 200);
            test.log(Status.PASS, "Account Activity data received - Status code 200");

        } catch (AssertionError error){
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }
    @Test
    @Tag("POST")
    public void transferFunds () {
        ExtentTest test = extent.createTest("Transfer Account Funds - BACK END - Successful Test");
        test.log(Status.INFO, "Tests-Begin");
        try {
            int customerId = loginAndGetCustomerId();
            test.log(Status.PASS, "Customer id successfully received");
            test.log(Status.INFO, "TRANSFER FUNDS Test - Begin");
            test.log(Status.INFO, "Getting accounts by Customer ID");
            Response resGetAccountId = RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customerId + "/accounts");
            XmlPath xmlPath = resGetAccountId.xmlPath();
            int originAccount = xmlPath.getInt("accounts.account[0].id");
            int destinationAccount= xmlPath.getInt("accounts.account[1].id");
            Assertions.assertEquals(resGetAccountId.statusCode(), 200);
            test.log(Status.PASS, "Accounts IDs received");
            test.log(Status.INFO, "Request Transfer");
            System.out.println("https://parabank.parasoft.com/parabank/services/bank/transfer?fromAccountId="+originAccount+"&toAccountId="+destinationAccount+"&amount=50");
            Response resPostTransferFunds = RestAssured.post("https://parabank.parasoft.com/parabank/services/bank/transfer?fromAccountId="+originAccount+"&toAccountId="+destinationAccount+"&amount=50");
            Assertions.assertEquals(resPostTransferFunds.statusCode(), 200);
            test.log(Status.PASS, "Transfer Account Funds successful response");

        } catch (AssertionError error) {
            test.log(Status.FAIL, "Validation failed: " + error.getLocalizedMessage());
            throw error;
        }
    }
    @AfterAll
    public static void getReport() {
        extent.flush();
    }
}
