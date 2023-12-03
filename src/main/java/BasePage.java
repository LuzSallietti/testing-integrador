import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected By usernameLoginInput = By.xpath("//input[@name='username']");
    protected By passwordLoginInput = By.xpath("//input[@name='password']");
    protected By loginButton = By.xpath("//input[@value='Log In']");
    protected By registerButton = By.xpath("//a[normalize-space()='Register']");
    protected By openAccountButton = By.xpath("//a[normalize-space()='Open New Account']");
    public static WebDriver driver;
    public static WebDriverWait wait;


    public BasePage(WebDriver driver, WebDriverWait wait) {
        BasePage.driver = driver;
        BasePage.wait = wait;
    }

    // métodos
    // setUp --> abre la ventana del navegador y la maximiza
    public void setUp() {
        driver.manage().window().maximize();
    }

    //obtiene la url
    public void getUrl(String url) {
        driver.get(url);
    }

    // cierra el navegador
    public void close() {
        driver.quit();
    }

    //buscar un elemento en la interfaz por su localizador
    protected WebElement elementFind(By locator) throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    // colocar texto (input)
    protected void sendText(String imputText, By locator) throws InterruptedException {
        this.elementFind(locator).clear();
        this.elementFind(locator).sendKeys(imputText);
    }
    // colocar clave
    protected void sendKey(CharSequence key, By locator) throws InterruptedException {
        this.elementFind(locator).sendKeys(key);
    }

    // hacer click
    protected void click(By locator) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        this.elementFind(locator).click();
    }
    // obtener texto
    protected String getText(By locator) throws InterruptedException {
        return this.elementFind(locator).getText();
    }
    //Loguearse

    public void login (String username, String password) throws InterruptedException {
        this.sendText(username, usernameLoginInput);
        this.sendText(password, passwordLoginInput);
        this.click(loginButton);
    }

    // hacer click en el boton Registrar
    public void registerClick() throws InterruptedException {
        this.click(registerButton);
    }

}
