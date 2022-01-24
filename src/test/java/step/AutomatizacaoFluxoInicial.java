package step;

import io.cucumber.java.After;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "@Fluxo, not @Titulo")
public class AutomatizacaoFluxoInicial {

    private WebDriver driver = null;

    @Before
    public void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/seleniumdrivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Given("Eu estou na pagina inicial da Araujo")
    public void acessarAraujo() {
        driver.get("https://www.araujo.com.br");
    }

    @When("Eu pesquiso por {string}")
    public void pesquisarPor(String query) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement inputPesquisa = driver.findElement(By.cssSelector("fieldset.busca input.fulltext-search-box"));
        inputPesquisa.sendKeys(query);
        inputPesquisa.submit();

    }

    @And("clico em comprar")
    public void comprarProduto(){

        WebElement btnAceitarCookies = driver.findElement(By.cssSelector("a.cc-btn.cc-allow"));
        btnAceitarCookies.click();

        WebElement primeiroItem = driver.findElement(By.cssSelector("ul#neemu-products-container li:first-child"));
        Actions action = new Actions(driver);
        action.moveToElement(primeiroItem).perform();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.findElement(By.cssSelector("ul#neemu-products-container li:first-child a.nm-buy-button")).isDisplayed());

        WebElement botaoComprar = driver.findElement(By.cssSelector("ul#neemu-products-container li:first-child a.nm-buy-button"));
        botaoComprar.click();
    }

    @And("adiciono o produto ao carrinho")
    public void adicionarProdutoCarrinho(){

        WebElement btnAceitarCookies = driver.findElement(By.cssSelector("a.cc-btn.cc-allow"));
        btnAceitarCookies.click();

        WebElement adcCarrinho = driver.findElement(By.cssSelector("a.buy-button"));
        Actions action = new Actions(driver);
        action.moveToElement(adcCarrinho).perform();

        WebElement btnAdcCarrinho = driver.findElement(By.cssSelector("a.buy-button"));
        btnAdcCarrinho.click();

    }

    @And("Removo o produto do carrinho")
    public void removerProdutoCarrinho(){

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button.x-minicart__item-remove")));


        WebElement rmvProduto = driver.findElement(By.cssSelector("button.x-minicart__item-remove"));
        Actions action = new Actions(driver);
        action.moveToElement(rmvProduto).perform();

        WebElement btnRmvProduto = driver.findElement(By.cssSelector("button.x-minicart__item-remove"));
        btnRmvProduto.click();
    }

    @Then("O produto deve ser removido do carrinho")
    public void validarRemocao() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.x-minicart__empty-text-container")));
    }

    @After()
    public void closeBrowser() throws IOException {
        driver.quit();
    }
}