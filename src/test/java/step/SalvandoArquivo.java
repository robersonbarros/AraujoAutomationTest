package step;

import io.cucumber.java.After;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "not @Fluxo, @Titulo")
public class SalvandoArquivo {

    private WebDriver driver = null;

    @Before
    public void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/seleniumdrivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Given("^Eu estou na pagina da Araujo")
    public void acessarAraujo() {
        driver.get("https://www.araujo.com.br");
    }

    @When("Eu pesquiso a palavra {string}")
    public void pesquisarPrdt(String query) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement inputPesquisa = driver.findElement(By.cssSelector("fieldset.busca input.fulltext-search-box"));
        inputPesquisa.sendKeys(query);
        inputPesquisa.submit();

    }

    @Then("^Eu registro o titulo da pagina em um arquivo")
    public void registrarTitulo() {
        String tituloPagina = driver.getTitle();
        try {
            FileWriter myWriter = new FileWriter("src/test/resources/titulos/titulo.txt");
            myWriter.write(tituloPagina);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After()
    public void closeBrowser() throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("src/test/resources/screenshots/screenshot.png"));
        driver.quit();
    }
}