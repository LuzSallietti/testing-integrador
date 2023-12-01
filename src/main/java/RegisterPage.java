import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends BasePage{
    private By title = By.xpath("//h1[normalize-space()='Signing up is easy!']");
    private By form = By.id("customerForm");
    private By firstName = By.id("customer.firstName");
    private By lastName= By.id("customer.lastName");
    private By address_street = By.id("customer.address.street");
    private By address_city = By.id("customer.address.city");
    private By address_state =By.id("customer.address.state");
    private By address_zipCode= By.id("customer.address.zipCode");
    private By phone = By.id("customer.phoneNumber");
    private By ssn = By.id("customer.ssn");
    private By username = By.id("customer.username");
    private By password = By.id("customer.password");
    private By repeatPassword = By.id("repeatedPassword");
    private By registerButton = By.xpath("//input[@value='Register']");
    private By confirmationMessage = By.xpath("//p[contains(text(),'Your account was created successfully. You are now')]");


    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getRegisterTitle() throws InterruptedException {
        return this.getText(title);
    }
    public String getRegisterForm() throws InterruptedException {
        String tagname = this.elementFind(form).getTagName();
        return tagname;
    }

    public void insertFirstName(String name) throws InterruptedException {
        sendText(name, firstName);
    }

    public void insertLastName(String lastname) throws InterruptedException {
        sendText(lastname, lastName);
    }

    public void insertStreet(String street) throws InterruptedException {
        sendText(street, address_street);
    }
    public void insertCity(String city) throws InterruptedException {
        sendText(city, address_city);
    }
    public void insertState(String state) throws InterruptedException{
        sendText(state, address_state);
    }
    public void insertZipCode(String zipCode) throws InterruptedException {
        sendText(zipCode, address_zipCode);
    }

    public void insertPhone(String telephone) throws InterruptedException{
        sendText(telephone, phone);
    }
    public void insertSsn(String SSN) throws InterruptedException {
        sendText(SSN, ssn);
    }
    public void insertUsername(String userName) throws InterruptedException {
        sendText(userName, username);
    }

    public void insertPassword(String pass) throws InterruptedException {
        sendText(pass, password);
    }

    public void confirmPassword(String pass) throws InterruptedException {
        sendText(pass, repeatPassword);
    }

    public void confirmRegister() throws InterruptedException{
        click(registerButton);
    }
    public String getConfirmation () throws InterruptedException{
        return elementFind(confirmationMessage).getText();
    }

}
